from kafka import KafkaConsumer
from mysql.connector import connect
from typing import NamedTuple
from datetime import datetime

class ApplicantsDTO(NamedTuple):
    passport: str
    priority_date: str
    DOB: str
    Title: str
    Given_Name: str
    Surname: str
    Gender: str
    Citizenship: str
    Country_Of_Birth: str
    Street_Address: str
    City_Of_Residence: str
    State: str
    ZIP: int
    Latitude: float
    Longitude: float

topic_name = 'applicants'
consumer = KafkaConsumer(bootstrap_servers=['b-2.kafkabimscluster.nb8nsc.c20.kafka.us-east-1.amazonaws.com:9092', 'b-1.kafkabimscluster.nb8nsc.c20.kafka.us-east-1.amazonaws.com:9092','b-3.kafkabimscluster.nb8nsc.c20.kafka.us-east-1.amazonaws.com:9092'])
consumer.subscribe(topics=topic_name)

try:

    mysqlDb = connect(
        host="mskdb-dev.cxfk1rsjobgo.us-east-1.rds.amazonaws.com",
        user="admin",
        password="Canopyone123",
        database="bims",
        port="3306"
    )

    for msg in consumer:
        # create a Cursor object for control
        cur = mysqlDb.cursor(buffered=True)
        print("%d: %d: v=%s" % (msg.partition,
                                msg.offset,
                                msg.value))
        # insert to mysql db
        insertStmt = "INSERT INTO applicants("
        selectStmt = "Select * from applicants where passport = "
        cols = ""
        values = ""
        firstCol: bool = True
        firstVal: bool = True
        message = msg.value.decode('unicode-escape').replace('[','').replace(']','').replace('"','').split(',')
        
        applicantsDTO = ApplicantsDTO(*message)
        for idx in range(len(ApplicantsDTO._fields)):
            val = applicantsDTO[idx].strip()
            field = ApplicantsDTO._fields[idx]
            if val != '':
                if firstCol:
                    cols = field
                    firstCol = False
                else:
                    cols = "{}, {}".format(cols, field)
                if (field == 'priority_date' or field == 'DOB'):
                    val = datetime.strptime(val, '%m/%d/%Y').strftime('%Y-%m-%d')
                if (field == 'ZIP' or field == 'Latitude' or field == 'Longitude'):
                    if firstVal:
                        values = val
                        firstVal = False
                    else :
                        values = "{}, {}".format(values, val)
                else:
                    if firstVal:
                        values = "\"{}\"".format(val)
                        firstVal = False
                    else :
                        values = "{}, \"{}\"".format(values, val)
        insertStmt = "{} {} ) VALUES ({});".format(insertStmt, cols, values)
        selectStmt = "{} \"{}\";".format(selectStmt, applicantsDTO.passport)
        
        
        print("Statement executing ", selectStmt)
        cur.execute(selectStmt)
        resp = cur.fetchone()
        print(resp)
        if (resp == None):
            print("Insert Statement executing ", insertStmt)
            cur.execute(insertStmt)
        
        mysqlDb.commit()

    mysqlDb.close()
except Exception as e:
    mysqlDb.commit()
    mysqlDb.close()
    print('Error while fetching from kafka and saving in mysqlDB, {}', e)
    raise e

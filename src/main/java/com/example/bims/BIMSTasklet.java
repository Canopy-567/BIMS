package com.example.bims;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class BIMSTasklet implements Tasklet {
    @Autowired
    OfficeRepository officeRepository;

    @Autowired
    ApplicantsRepository applicantsRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        List<Office> offices = officeRepository.findAll();
        Set<Applicants> applicants = applicantsRepository.findByPriority_DateBetween("2022=02-09", "2022=02-16");
        List<org.apache.commons.lang3.tuple.Pair<Double, Double>> officesList = new ArrayList<>();

        for(Office office: offices){
            Pair pair = Pair.of(office.getLongitude(), office.getLatitude());
            officesList.add(pair);
        }

        for(Applicants applicant: applicants){
            StringBuilder uri = new StringBuilder("http://router.project-osrm.org/table/v1/driving/");
            StringBuilder params = new StringBuilder("?annotations=distance,duration&sources=0&destinations=");
            // append source to uri first
            uri.append(applicant.getLongitude())
                    .append(",")
                    .append(applicant.getLatitude())
                    .append(";");

            for(int i=0; i<officesList.size(); i++) {
                uri.append(officesList.get(i).getLeft())
                        .append(",")
                        .append(officesList.get(i).getRight())
                        .append(";");
                params.append(i+1).append(";");
            }

            params.deleteCharAt(params.length() - 1);
            ResponseEntity<MapAPIResponse> response = restTemplate.getForEntity(uri.toString() + params.toString(), MapAPIResponse.class);

            if(response.getStatusCode() == HttpStatus.OK) {
                MapAPIResponse mapAPIResponse = response.getBody();
                if(mapAPIResponse.getCode().equalsIgnoreCase("Ok")){
                    //fetch top 3 durations and their corresponding locations
                    ArrayList<Integer> fastestDistanceList = get3Fastest(mapAPIResponse.getDurations().get(0));

                    // check and allot slot
                    checkAndAllotSlot(fastestDistanceList, applicant, officesList);

                } else {
                    log.error("Error while retrieving data from Map API {}", mapAPIResponse.getCode());
                }
            }
        }

        return RepeatStatus.FINISHED;
    }

    private void checkAndAllotSlot(ArrayList<Integer> fastestDistanceList, Applicants applicants, List<Pair<Double, Double>> officesList) {
        // check slots for specific office if available allot and save in db
        // check next one,
        // check next one,
        // no slots available, mark them for next day
    }

    private ArrayList<Integer> get3Fastest(List<Integer> distance)
    {
        int n = distance.size();
        int firstmin = Integer.MAX_VALUE;
        int firstIndex = -1;
        int secmin = Integer.MAX_VALUE;
        int secIndex = -1;
        int thirdmin = Integer.MAX_VALUE;
        int thirdIndex = -1;

        for (int i = 0; i < n; i++)
        {
            if (distance.get(i) < firstmin)
            {
                thirdmin = secmin;
                secmin = firstmin;
                firstmin = distance.get(i);
                thirdIndex = secIndex;
                secIndex = firstIndex;
                firstIndex = i;
            }
            else if (distance.get(i) < secmin)
            {
                thirdmin = secmin;
                secmin = distance.get(i);
                thirdIndex = secIndex;
                secIndex = i;
            }

                /* Check if current element is less than
                then update third */
            else if (distance.get(i) < thirdmin){
                thirdmin = distance.get(i);
                thirdIndex = i;
            }
        }

        ArrayList<Integer> fastestDistanceList = new ArrayList<>();
        fastestDistanceList.add(firstIndex);
        fastestDistanceList.add(secIndex);
        fastestDistanceList.add(thirdIndex);
        return fastestDistanceList;
    }
}

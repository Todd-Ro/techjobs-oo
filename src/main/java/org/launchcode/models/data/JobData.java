package org.launchcode.models.data;

import javafx.geometry.Pos;
import org.launchcode.models.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
public class JobData {

    private ArrayList<Job> jobs = new ArrayList<>(); // An instance starts with an ArrayList of jobs
    private static JobData instance;

    private JobFieldData<Employer> employers = new JobFieldData<>();
    private JobFieldData<Location> locations = new JobFieldData<>();
    private JobFieldData<CoreCompetency> coreCompetencies = new JobFieldData<>();
    private JobFieldData<PositionType> positionTypes = new JobFieldData<>();
    // There is an instance of the JobFieldData class for each field type.
    // This instance contains an ArrayList of all the Employers/Locations in our data.
    // JobFieldData has an add method that allows a particular JobField subclass instance to be added to the ArrayList.


    private JobData() {
        JobDataImporter.loadData(this);
    } // Constructor calls importer's loadData method
    // loadData method populates the jobs ArrayList using JobData's add method to add jobs
    // loadData also populates the ArrayLists in each JobFieldData by calling their add method
    // Thus, when our JobData instance is constructed, the jobs ArrayList of Jobs that this class has, ...
            // ... and the JobFields with their ArrayLists of employers/locations/etc., are populated by the importer.


    public static JobData getInstance() {
        if (instance == null) {
            instance = new JobData();
        }
        return instance;
    } //All controllers except the HomeController call getInstance, which calls the constructor to create a static ...
            // instance of JobData, simply called instance.
            //This means that as soon as the first line within a controller class is run, there will be a JobData ...
            //instance called "instance" that has a

    public Job findById(int id) {
        for (Job job : jobs) {
            if (job.getId() == id)
                return job;
        }

        return null;
    }

    public ArrayList<Job> findAll() {
        return jobs;
    }


    public ArrayList<Job> findByColumnAndValue(JobFieldType column, String value) {
        //When calling this method, remember to specify the first parameter as something like JobFieldType.POSITION_TYPE
        ArrayList<Job> matchingJobs = new ArrayList<>();

        for (Job job : jobs) {
            if (getFieldByType(job, column).contains(value))
                matchingJobs.add(job);
        }

        return matchingJobs;
    }


    public ArrayList<Job> findByValue(String value) {

        ArrayList<Job> matchingJobs = new ArrayList<>();

        for (Job job : jobs) {

            if (job.getName().toLowerCase().contains(value)) {
                matchingJobs.add(job);
                continue;
            }

            for (JobFieldType column : JobFieldType.values()) {
                //The JobFieldType enum in forms, in addition to "ALL", has EMPLOYER, LOCATION, ...
                        // CORE_COMPETENCY, and POSITION_TYPE
                if (column != JobFieldType.ALL && getFieldByType(job, column).contains(value)) {
                    // This if statement checks whether the specified job field for this job, designated column, ...
                            // contains the value passed into findByValue, using the JobField's contains method.
                    matchingJobs.add(job);
                    break;
                }
            }
        }

        return matchingJobs;
    }


    public void add(Job job) {
        jobs.add(job);
    }


    private static JobField getFieldByType(Job job, JobFieldType type) {
        // Based on the enum value, this method returns the specific field (which has a value variable and ...
                // a contains method, among other things) for this job. It calls getters from the job that ...
                // are coded to return that job's instance of the JobField subclass associated with that JobFieldType.
        switch(type) {
            case EMPLOYER:
                return job.getEmployer();
            case LOCATION:
                return job.getLocation();
            case CORE_COMPETENCY:
                return job.getCoreCompetency();
            case POSITION_TYPE:
                return job.getPositionType();
        }

        throw new IllegalArgumentException("Cannot get field of type " + type);
    }

    public JobFieldData<Employer> getEmployers() {
        return employers;
    }

    public JobFieldData<Location> getLocations() {
        return locations;
    }

    public JobFieldData<CoreCompetency> getCoreCompetencies() {
        return coreCompetencies;
    }

    public JobFieldData<PositionType> getPositionTypes() {
        return positionTypes;
    }
}

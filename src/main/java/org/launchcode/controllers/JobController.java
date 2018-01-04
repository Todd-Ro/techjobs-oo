package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.launchcode.models.data.JobFieldData;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        model.addAttribute("title", "Job Fields for this Job");
        // TODO #1 - get the Job with the given ID and pass it into the view
        Job resultJob = jobData.findById(id);
        model.addAttribute("thisJob",resultJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        // The constructor for a job is ...
            /** public Job(String aName, Employer aEmployer, Location aLocation,
            PositionType aPositionType, CoreCompetency aSkill) { **/

        if (errors.hasErrors()) {
            return "new-job";
        }

        JobData jobData = jobForm.getJobData();
        JobFieldData<Employer> employers = jobData.getEmployers();
        JobFieldData<Location> locations = jobData.getLocations();
        JobFieldData<PositionType> positionTypes = jobData.getPositionTypes();
        JobFieldData<CoreCompetency> coreCompetencies = jobData.getCoreCompetencies();
        Employer thisEmployer = employers.findById(jobForm.getEmployerId());
        Location thisLocation = locations.findById(jobForm.getLocationId());
        PositionType thisPositionType = positionTypes.findById(jobForm.getPositionTypeId());
        CoreCompetency thisCoreCompetency = coreCompetencies.findById(jobForm.getCoreCompetencyId());
        Job newJob = new Job(jobForm.getName(), thisEmployer, thisLocation, thisPositionType, thisCoreCompetency);
        jobData.add(newJob);
        int id =newJob.getId();
        return "redirect:?id="+id;

    }
}

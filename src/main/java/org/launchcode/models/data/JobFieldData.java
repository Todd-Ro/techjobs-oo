package org.launchcode.models.data;

import org.launchcode.models.Job;
import org.launchcode.models.JobField;

import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
public class JobFieldData<T extends JobField> {
    //Each Job is assigned one instance of each subclass of JobField in forms (such as Employer) representing the ...
            // ... one that is applicable to that job, which may be the same one that is assigned to another job.
    //However, our JobData instance is assigned one instance of this class for each field type.
    //This class is assigned a T for one of the classes that extends JobField.
    //This class has an ArrayList of  its particular subclass of JobField.
            // This ArrayList can contain, for example, all Employers.

    private ArrayList<T> allFields = new ArrayList<>();

    public ArrayList<T> findAll() {
        return allFields;
    }

    public T findById(int id) {
        for (T item : allFields) {
            if (item.getId() == id)
                return item;
        }

        return null;
    }

    public void add(T item) {
        allFields.add(item);
    }

    T findByValue(String value) {
        for (T item : allFields) {
            if (item.contains(value))
                return item;
        }

        return null;
    }

}

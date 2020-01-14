package pl.ug.tuj.lab4;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonList implements IListOperation<Person> {
    private List<Person> personList = new ArrayList<>();

    @Value("${canAdd}")
    private String canAdd;


    @Override
    public Person get(int id) {
        for(Person p : personList) {
            if(p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        Person toBeDeleted = null;

        for(Person p : personList) {
            if(p.getId() == id) {
                toBeDeleted = p;
                break;
            }
        }

        if(toBeDeleted == null) {
            return false;
        }

        return personList.remove(toBeDeleted);
    }

    @Override
    public boolean add(Person element) {
        for(Person p : personList) {
            if(p.getId() == element.getId()) {
                return false;
            }
        }

        if(canAdd.equals("true")){
            personList.add(element);
            return true;
        }

        return false;
    }

    @Override
    public void clear() {
        this.personList = new ArrayList<>();
    }
}

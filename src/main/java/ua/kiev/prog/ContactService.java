package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private GroupRepository groupRepository;



    @Transactional
    public void addContact(Task contact) {
        contactRepository.save(contact);
    }

    @Transactional
    public void addGroup(Group group) {
        groupRepository.save(group);
    }



    @Transactional
    public void deleteContacts(long[] idList) {
        for (long id : idList)
            contactRepository.deleteById(id);
    }

    @Transactional(readOnly=true)
    public List<Group> findGroups() {
        return groupRepository.findAll();
    }

    @Transactional(readOnly=true)
    public List<Task> findAll(Pageable pageable) {
        return contactRepository.findAll(pageable).getContent();
    }


    @Transactional(readOnly=true)
    public List<Task> findAll() {
        return contactRepository.findAll();
    }


    @Transactional(readOnly=true)
    public List<Task> findByGroup(Group group, Pageable pageable) {
        return contactRepository.findByGroup(group, pageable);
    }

    @Transactional(readOnly = true)
    public long countByGroup(Group group) {
        return contactRepository.countByGroup(group);
    }

    @Transactional(readOnly=true)
    public List<Task> findByPattern(String pattern, Pageable pageable) {
        return contactRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public long count() {
        return contactRepository.count();
    }

    @Transactional(readOnly=true)
    public Group findGroup(long id) {
        return groupRepository.findById(id).get();
    }

    @Transactional
    public void reset() {
        groupRepository.deleteAll();
        contactRepository.deleteAll();

        Group group = new Group("Test");
        Task contact;

        addGroup(group);

        for (int i = 0; i < 13; i++) {
            contact = new Task(null, "Name" + i, "Surname" + i, "description" + i, "date" + i, "066227277"+i);
            addContact(contact);
        }
        for (int i = 0; i < 10; i++) {
            contact = new Task(group, "Name" + i, "Surname" + i, "description" + i, "date" + i, "066227277"+i);
            addContact(contact);
        }
    }
}

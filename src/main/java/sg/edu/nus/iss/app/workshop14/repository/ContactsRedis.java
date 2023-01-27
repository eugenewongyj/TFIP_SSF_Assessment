package sg.edu.nus.iss.app.workshop14.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.workshop14.models.Contact;

@Service
public class ContactsRedis {
    private static final String CONTACT_ENTITY = "contactlist";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void save(Contact contact) {
        redisTemplate.opsForList().leftPush(CONTACT_ENTITY, contact.getId());
        redisTemplate.opsForHash().put(CONTACT_ENTITY + "_Map", contact.getId(), contact);
    }

    public Contact findById(String contactId) {
        System.out.println("In service");
        Contact result= (Contact) redisTemplate.opsForHash().get(CONTACT_ENTITY+ "_Map", contactId);
        return result;
    }

    public List<Contact> findAll(int startIndex) {
        // List<String>
        // List<Object> fromContactList = redisTemplate.opsForList().range(CONTACT_ENTITY, startIndex, 10);
        List<Object> fromContactList = redisTemplate.opsForList().range(CONTACT_ENTITY, startIndex, 10);

        List<Contact> contacts = redisTemplate.opsForHash().multiGet(CONTACT_ENTITY + "_Map", fromContactList)
                                    .stream()
                                    .filter(Contact.class::isInstance)
                                    .map(Contact.class::cast)
                                    .toList();

        return contacts;
    }
    
}

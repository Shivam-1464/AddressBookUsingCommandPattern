import responses.AllContactsList
import responses.ContactsPresentInGroupList
import responses.GroupsPresentInContactList
import java.lang.Exception
import java.util.UUID

object Storage {
    private var contactMap = mutableMapOf<UUID, Contact>()
    private var emailMap = mutableMapOf<UUID, Map<String, Email>>()
    private var phoneMap = mutableMapOf<UUID, Map<String, Phone>>()
    private var addressMap = mutableMapOf<UUID, Map<String, Address>>()

    private var groupMap = mutableMapOf<UUID, String>()
    private var groupMembersMap = mutableMapOf<UUID, List<UUID>>()

//    private var mapContactsByPhoneNumber = mutableMapOf<String, UUID>()



    fun addContact(contact: Contact): Contact {
        contactMap[contact.id] = contact
        return contact
    }

    fun updateContact(contact: Contact): Contact{
        contactMap[contact.id] = contact
        return contact
    }

    fun addEmails(contactId: UUID, emails: List<Email>): List<Email> {
        emailMap[contactId] = emails.associateBy { it.emailid }
        return emails
    }

    fun addPhones(contactId: UUID, phones: List<Phone>):List<Phone>{
        phoneMap[contactId] = phones.associateBy{it.number}
        return phones
    }

    fun addAddresses(contactId: UUID, addresses: List<Address>): List<Address>{
        addressMap[contactId] = addresses.associateBy { it.type }
        return addresses
    }

    fun listAllContacts(): List<AllContactsList>{


        val contacts: MutableList<AllContactsList> = mutableListOf();
        contactMap.values.forEach{
            val singleContactId = it.id
            val name = it.fname + " " +it.lname
            val phone = phoneMap.get(it.id)!!
            val email = emailMap.get(it.id)!!
            val address = addressMap.get(it.id)!!
            contacts.add(AllContactsList(
                name, phone, email, address
            ))
        }
        return contacts
    }
    fun fetchContact(id: UUID): Contact {
        return if (id in contactMap) {
            contactMap[id]!!
        } else {
            throw Exception("Contact not found with id: $id")
        }
    }

    fun searchContactByNumber(num: String) : Contact {
        var resultMap = phoneMap.filterValues { it.keys.contains(num)}
        var id : UUID = UUID.randomUUID()
        resultMap.forEach{
            id = it.key
        }
//        println(fetchContact(id))
        return fetchContact(id)
    }


    fun getEmailsByContactId(contactId: UUID): List<Email> {
        return if (contactId in emailMap) {
            emailMap[contactId]!!.values.toList()
        } else {
            throw Exception("Emails not found for contact: $contactId")
        }
    }

    fun getPhonesByContactId(contactId: UUID): List<Phone> {
        return if (contactId in phoneMap) {
            phoneMap[contactId]!!.values.toList()
        } else {
            throw Exception("Emails not found for contact: $contactId")
        }
    }

    fun getAddressesByContactId(contactId: UUID): List<Address> {
        return if (contactId in addressMap) {
            addressMap[contactId]!!.values.toList()
        } else {
            throw Exception("Emails not found for contact: $contactId")
        }
    }

    fun removeContact(id: UUID): Contact? {
        return if(id in contactMap) {
            contactMap.remove(id)
        } else {
            throw Exception("Contact not found with id: $id")
        }
    }

    fun createGroup(group: Group): Group{
        groupMap[group.id] = group.gpname
        addGroupMembers(group.id, group)
        return group
    }
    fun addGroupMembers(id: UUID, group: Group){
        groupMembersMap[id] = group.members
    }
    fun listAllGroups(): List<String>{
        return groupMap.values.toList()
    }
    fun deleteGroup(id: UUID): String?{
        if(id in groupMap) {
            groupMembersMap.remove(id)
            return groupMap.remove(id)
        } else {
            throw Exception("Contact not found with id: $id")
        }
    }

    fun listContactsPresentInGroup(id: UUID): ContactsPresentInGroupList {
        val contactIds = groupMembersMap[id] ?: emptyList()
        val contacts = contactIds.mapNotNull { contactMap[it] }
        val groupName = groupMap[id]!!

        return ContactsPresentInGroupList(
            groupName = groupName,
            contacts = contacts
        )
    }

    fun listGroupsPresentInContact(id: UUID) : GroupsPresentInContactList{
        val contact = contactMap[id]!!
        val groupIds = groupMembersMap.filterValues { it.contains(id) }.keys
        println(groupIds)
        var groups: MutableList<String> = mutableListOf()
        groupIds.forEach{
           groups.add(groupMap[it]!!)
        }

        return GroupsPresentInContactList(
            contactName = contact.fname + " "+ contact.lname,
            groups = groups
        )
    }
//    fun deleteContactsFromGroup(groupId: UUID, contactId: UUID){
//        groupMembersMap[groupId].forEach {
//            if (it.equals(contactId)){
//
//            }
//        }
//    }
}
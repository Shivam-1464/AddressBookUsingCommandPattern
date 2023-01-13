package addressbook

import Address
import Email
import Phone
import com.example.addressbook.commands.*
import requests.ContactCreateRequest
import requests.ContactUpdateRequest
import requests.GroupCreateRequest
import responses.ContactResponse
import java.util.UUID

fun main(args: Array<String>) {

//    val numbers = listOf<Int>(1,2,3,4,5,6)
//    println(numbers.map { it*it })
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.

    val req = ContactCreateRequest(
        fname = "Shivam",
        lname = "Chavda",
        emails = listOf(
            Email("Home", "9999998098098@gmail.com"),
            Email("Office", "88888888888@gmail.com")
        ),
        phones = listOf(
            Phone("personal","7405222732"),
            Phone("work", "7878787878")
        ),
        addresses = listOf(
            Address("home", "undera"),
            Address("work", "alkapuri")
        )
    )
    val req2 = ContactCreateRequest(
        fname = "Shivam",
        lname = "Vayana",
        emails = listOf(
            Email("Home", "9999998098098@gmail.com"),
            Email("Office", "88888888888@gmail.com")
        ),
        phones = listOf(
            Phone("personal","9898989898"),
            Phone("work", "7878787878")
        ),
        addresses = listOf(
            Address("home", "undera"),
            Address("work", "alkapuri")
        )
    )

    val addContactCmd = AddContactCommand(req)
    val createdContact = addContactCmd.execute() as ContactResponse

    val addContactCommand2 = AddContactCommand(req2)
    val createdContact2 = addContactCommand2.execute()
//    println(createdContact)


    val updatereq = ContactUpdateRequest(
        id = createdContact.id,
        fname = "Shivam",
        lname = "Chavda",
        emails = listOf(
            Email("Home", "shivam@gmail.com"),
            Email("Office", "vayana@gmail.com")
        ),
        phones = listOf(
            Phone("personal","7405222732"),
            Phone("work", "7878787878")
        ),
        addresses = listOf(
            Address("home", "undera"),
            Address("work", "alkapuri")
        )
    )

//    val gg = UpdateContactCommand(updatereq)
//    val gg2 = gg.execute()

//    println((UpdateContactCommand(updatereq)).execute())
//    (DeleteContactCommand(createdContact.id)).execute()
//    (ListAllContactsCommand()).execute()
//    println((FindContactByIdCommand(createdContact.id)).execute())
//    println((FindContactByNumberCommand("7405222732")).execute())

    val groupreq = GroupCreateRequest("vayana", mutableListOf(createdContact.id,createdContact2.id))
    val createdGroup = CreateGroupCommand(groupreq).execute()
//    println(createdGroup)

    val groupreq2 = GroupCreateRequest("nuv", mutableListOf(createdContact.id,createdContact2.id))
    val createdGroup2 = CreateGroupCommand(groupreq2).execute()
//    println(createdGroup2)

//    println((ListAllGroupsCommand()).execute())
//    (DeleteGroupCommand(createdGroup.groupId)).execute()

//    println((ListContactsPresentInGroups(createdGroup.groupId)).execute())

    println((ListGroupsPresentInContacts(createdContact.id)).execute())




//    val gp1 = AddressBookOperations.createGroup(GroupCreateRequest("vayana", mutableListOf(createdContact.id,createdContact2.id)))




//    val fetchedContact = AddressBookOperations.fetchContact(createdContact.id)
//    println(fetchedContact)
//
//    AddressBookOperations.removeContact(createdContact.id)
//    AddressBookOperations.listAllContacts()
//    AddressBookOperations.searchContactByNumber("7405222732")

}
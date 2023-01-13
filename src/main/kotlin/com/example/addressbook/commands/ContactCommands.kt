package com.example.addressbook.commands

import Contact
import Group
import Storage
import requests.ContactCreateRequest
import requests.ContactUpdateRequest
import requests.GroupCreateRequest
import responses.ContactResponse
import responses.ContactsPresentInGroupList
import responses.GroupResponse
import responses.GroupsPresentInContactList
import java.util.*

fun ContactCreateRequest.toContact() =
    Contact(
        id = UUID.randomUUID(),
        fname = this@toContact.fname,
        lname = this@toContact.lname
    )

fun ContactUpdateRequest.toUpdateContact() =
    Contact(
        id = this@toUpdateContact.id,
        fname = this@toUpdateContact.fname,
        lname = this@toUpdateContact.lname
    )

//fun ContactUpdateRequest.toReqContact() =
//    Contact(
//        id = this@toReqContact.id,
//        fname = this@toReqContact.fname,
//        lname = this@toReqContact.lname
//    )
fun GroupCreateRequest.toGroup() =
    Group(
        id = UUID.randomUUID(),
        gpname = this@toGroup.name,
        members = this@toGroup.members
    )

class AddContactCommand(
    private val request: ContactCreateRequest
) : Command {
    override fun execute(): ContactResponse {
        val contact = Storage.addContact(request.toContact())
        val emails = Storage.addEmails(contact.id, request.emails)
        val phones = Storage.addPhones(contact.id, request.phones)
        val addresses = Storage.addAddresses(contact.id, request.addresses)
        return ContactResponse(
            id = contact.id,
            firstName = contact.fname,
            lastName = contact.lname,
            emails = emails,
            phones = phones,
            addresses = addresses
        )
    }
}

class CreateGroupCommand(
    private val groupRequest: GroupCreateRequest
): Command{
    override fun execute() : GroupResponse{
        val group = Storage.createGroup(groupRequest.toGroup())
        return GroupResponse(
            groupId = group.id,
            groupName = group.gpname
        )
    }
}

class ListAllGroupsCommand: Command{
    override fun execute(): List<String> {
        return Storage.listAllGroups()
    }
}

class DeleteGroupCommand(
    private val id: UUID
): Command{
    override fun execute() {
        println(Storage.deleteGroup(id))
    }
}

class ListContactsPresentInGroups(
    private val id: UUID
):Command{
    override fun execute(): ContactsPresentInGroupList {
        return Storage.listContactsPresentInGroup(id)
    }
}

class ListGroupsPresentInContacts(
    private val id: UUID
): Command  {
    override fun execute() : GroupsPresentInContactList {
        return Storage.listGroupsPresentInContact(id)
    }
}

class UpdateContactCommand(
    private val updateRequest: ContactUpdateRequest
): Command{
    override fun execute(): ContactResponse {
        val contact = Storage.updateContact(updateRequest.toUpdateContact())
        val emails = Storage.addEmails(contact.id, updateRequest.emails)
        val phones = Storage.addPhones(contact.id, updateRequest.phones)
        val addresses = Storage.addAddresses(contact.id, updateRequest.addresses)
        return ContactResponse(
            id = contact.id,
            firstName = contact.fname,
            lastName = contact.lname,
            emails = emails,
            phones = phones,
            addresses = addresses
        )
    }
}

class DeleteContactCommand(
    private val id: UUID
): Command{
    override fun execute() {
        Storage.removeContact(id)
    }
}

class ListAllContactsCommand: Command{
    override fun execute() {
        Storage.listAllContacts().forEach{
            println(it.name)
            println(it.phone?.values)
            println(it.email?.values)
            println(it.address?.values)
        }
    }
}

class FindContactByIdCommand(
    private val id: UUID
): Command{
    override fun execute(): ContactResponse {
        val contact = Storage.fetchContact(id)
        val emails = Storage.getEmailsByContactId(id)
        val phones = Storage.getPhonesByContactId(id)
        val addresses = Storage.getAddressesByContactId(id)
        return ContactResponse(
            id = contact.id,
            firstName = contact.fname,
            lastName = contact.lname,
            emails = emails,
            phones = phones,
            addresses = addresses
        )
    }
}

class FindContactByNumberCommand(
    private val num: String
): Command{
    override fun execute(): ContactResponse {
        val contact = Storage.searchContactByNumber(num)
        val emails = Storage.getEmailsByContactId(contact.id)
        val phones = Storage.getPhonesByContactId(contact.id)
        val addresses = Storage.getAddressesByContactId(contact.id)
        return ContactResponse(
            id = contact.id,
            firstName = contact.fname,
            lastName = contact.lname,
            emails = emails,
            phones = phones,
            addresses = addresses
        )
    }
}


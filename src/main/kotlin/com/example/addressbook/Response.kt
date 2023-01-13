package responses

import Address
import Contact
import Email
import Group
import Phone
import java.util.UUID

data class ContactResponse(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val emails: List<Email>,
    val phones: List<Phone>,
    val addresses : List<Address>
)

data class AllContactsList(
    val name: String,
    val phone: Map<String, Phone>,
    val email: Map<String, Email>,
    val address: Map<String, Address>
)

data class ContactsPresentInGroupList(
    val groupName: String?,
    val contacts: List<Contact>,
)

data class GroupsPresentInContactList(
    val contactName: String,
    val groups: List<String>

)

data class SearchByNumberResult(
    val name: String,
    val email: Map<String, Email>,
    val address: Map<String, Address>
)

data class GroupResponse(
    val groupId: UUID,
    val groupName: String
)
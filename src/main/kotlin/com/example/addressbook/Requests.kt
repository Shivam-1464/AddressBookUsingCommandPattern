package requests

import Address
import Email
import Phone
import java.util.UUID

data class ContactCreateRequest(
    val fname: String,
    val lname: String,
    val emails: List<Email>,
    val phones: List<Phone>,
    val addresses: List<Address>
)
data class ContactUpdateRequest(
    val id: UUID,
    val fname: String,
    val lname: String,
    val emails: List<Email>,
    val phones: List<Phone>,
    val addresses: List<Address>
)
data class GroupCreateRequest(
    val name: String,
    val members : MutableList<UUID>
)
import java.util.*

data class Contact(
    val id: UUID,
    val fname: String,
    val lname: String,
)
data class Email(
    val type: String,
    val emailid : String
)
data class Phone(
    val type: String,
    val number: String
)
data class Address(
    val type: String,
    val detail : String
)

data class Group(
    val id: UUID,
    val gpname: String,
    val members: MutableList<UUID>
)

//data class GroupMembers(
//    val id: UUID,
//    val members: MutableList<UUID>
//)

package ashutosh.jharkhand.regionallanguageadminapp.models

data class Question(
    val id: String,
    val question: String,
    val opt1: String,
    val opt2: String,
    val opt3: String,
    val opt4: String,
    val correctAnswer: Int
)

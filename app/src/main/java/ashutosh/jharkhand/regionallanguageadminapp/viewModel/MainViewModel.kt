package ashutosh.jharkhand.regionallanguageadminapp.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.jharkhand.regionallanguageadminapp.models.Category
import ashutosh.jharkhand.regionallanguageadminapp.models.Question
import ashutosh.jharkhand.regionallanguageadminapp.models.Set
import ashutosh.jharkhand.regionallanguageadminapp.models.Topic
import ashutosh.jharkhand.regionallanguageadminapp.utils.Constants
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val db = Firebase.firestore

    private val _selectedImageInAddCategory = MutableLiveData<String>()
    val selectedImageInAddCategory: LiveData<String>
        get() = _selectedImageInAddCategory

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    private val _currentTopics = MutableLiveData<List<Topic>>()
    val currentTopics: LiveData<List<Topic>>
        get() = _currentTopics

    private val _currentSets = MutableLiveData<List<Set>>()
    val currentSets: LiveData<List<Set>>
        get() = _currentSets

    private val _currentQuestion = MutableLiveData<List<Question>>()
    val currentQuestion: LiveData<List<Question>>
        get() = _currentQuestion

    val categoryNameToAddCategory = MutableLiveData<String>()

    val question = MutableLiveData<String>()
    val option1 = MutableLiveData<String>()
    val option2 = MutableLiveData<String>()
    val option3 = MutableLiveData<String>()
    val option4 = MutableLiveData<String>()
    val correctAnswer = MutableLiveData<Int>()

    private val topicChangeEventListener = EventListener<QuerySnapshot> { snapshot, error ->
        if (error != null) {
            Toast.makeText(getApplication(), error.message, Toast.LENGTH_SHORT).show()
            return@EventListener
        }

        if (snapshot != null) {
            val newTopics = ArrayList<Topic>()
            val documents = snapshot.documents
            for (document in documents) {
                newTopics.add(
                    Topic(
                        document.id,
                        document.getString(Constants.TOPIC_NAME_FIELD) ?: ""
                    )
                )
            }
            _currentTopics.value = newTopics
        }
    }

    private val setChangeEventListener = EventListener<QuerySnapshot> { snapshot, error ->
        if (error != null) {
            Toast.makeText(getApplication(), error.message, Toast.LENGTH_SHORT).show()
            return@EventListener
        }

        if (snapshot != null) {
            val newSets = ArrayList<Set>()
            val documents = snapshot.documents
            for (document in documents) {
                newSets.add(Set(document.id, (document.get(Constants.SET_NUMBER_FIELD) as Long?)?.toInt() ?: 0))
            }
            _currentSets.value = newSets
        }
    }

    private val questionChangeEventListener = EventListener<QuerySnapshot> { snapshot, error ->
        if (error != null) {
            Toast.makeText(getApplication(), error.message, Toast.LENGTH_SHORT).show()
            return@EventListener
        }

        if (snapshot != null) {
            val newQuestions = ArrayList<Question>()
            val documents = snapshot.documents
            for (document in documents) {
                newQuestions.add(
                    Question(
                        document.id,
                        document.getString(Constants.QUESTION_QUESTION_FIELD)?: "",
                        document.getString(Constants.QUESTION_OPTION_1_FIELD)?: "",
                        document.getString(Constants.QUESTION_OPTION_2_FIELD)?: "",
                        document.getString(Constants.QUESTION_OPTION_3_FIELD)?: "",
                        document.getString(Constants.QUESTION_OPTION_4_FIELD)?: "",
                        (document.get(Constants.QUESTION_CORRECT_ANSWER_FIELD) as Long?)?.toInt() ?: 0,
                    )
                )
            }
            _currentQuestion.value = newQuestions
        }
    }

    init {
        getRealtimeCategories()
    }

    fun updateTopics(categoryId: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId).collection(Constants.TOPIC_COLLECTION)
            .addSnapshotListener(topicChangeEventListener)
    }

    fun updateSets(categoryId: String, topicId: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId)
            .collection(Constants.TOPIC_COLLECTION).document(topicId)
            .collection(Constants.SET_COLLECTION)
            .orderBy(Constants.SET_NUMBER_FIELD, Query.Direction.ASCENDING)
            .addSnapshotListener(setChangeEventListener)
    }

    fun updateQuestions(categoryId: String, topicId: String, setId: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId)
            .collection(Constants.TOPIC_COLLECTION).document(topicId)
            .collection(Constants.SET_COLLECTION).document(setId)
            .collection(Constants.QUESTION_COLLECTION)
            .addSnapshotListener(questionChangeEventListener)
    }

    fun removeTopicSnapshotListener(categoryId: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId).collection(Constants.TOPIC_COLLECTION)
            .addSnapshotListener(topicChangeEventListener).remove()
    }

    fun removeSetSnapshotListener(categoryId: String, topicId: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId).collection(Constants.TOPIC_COLLECTION)
            .document(topicId).collection(Constants.SET_COLLECTION)
            .addSnapshotListener(setChangeEventListener).remove()
    }

    fun removeQuestionSnapshotListener(categoryId: String, topicId: String, setId: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId)
            .collection(Constants.TOPIC_COLLECTION).document(topicId)
            .collection(Constants.SET_COLLECTION).document(setId)
            .collection(Constants.QUESTION_COLLECTION)
            .addSnapshotListener(questionChangeEventListener).remove()
    }

    private fun getRealtimeCategories() {
        db.collection(Constants.CATEGORIES_COLLECTION).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Toast.makeText(getApplication(), error.message, Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val documents = snapshot.documents
                val newCategories = ArrayList<Category>()
                for (document in documents) {
                    newCategories.add(
                        Category(
                            document.id,
                            document.getString(Constants.CATEGORIES_NAME_FIELD) ?: "",
                            document.getString(Constants.CATEGORIES_IMAGE_FIELD) ?: ""
                        )
                    )
                }
                _categories.value = newCategories
            }
        }
    }

    fun onIconSelected(encodedImage: String) {
        _selectedImageInAddCategory.value = encodedImage
    }

    fun addCategoryFinished() {
        categoryNameToAddCategory.value = ""
        _selectedImageInAddCategory.value = ""
    }

    fun addCategoryToFirebase(): Boolean {

        if (!isValidCategory()) {
            return false
        }

        val category = hashMapOf(
            Constants.CATEGORIES_NAME_FIELD to categoryNameToAddCategory.value!!.trim(),
            Constants.CATEGORIES_IMAGE_FIELD to selectedImageInAddCategory.value!!,
        )

        db.collection(Constants.CATEGORIES_COLLECTION)
            .add(category)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(getApplication(), "Successfully added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(getApplication(), "Failed to add category", Toast.LENGTH_SHORT).show()
            }

        return true
    }

    private fun isValidCategory(): Boolean {
        if (categoryNameToAddCategory.value == null || categoryNameToAddCategory.value!!.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Enter valid category name", Toast.LENGTH_SHORT).show()
            return false
        } else if (selectedImageInAddCategory.value == null || selectedImageInAddCategory.value!!.isEmpty()) {
            Toast.makeText(getApplication(), "please select icon", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

    fun addNewTopicToFirebase(categoryId: String, topicName: String) {
        if (topicName.isEmpty()) {
            Toast.makeText(getApplication(), "Invalid Topic Name!! try again", Toast.LENGTH_SHORT).show()
            return
        }

        val topic = hashMapOf(Constants.TOPIC_NAME_FIELD to topicName)

        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId)
            .collection(Constants.TOPIC_COLLECTION)
            .add(topic)
            .addOnSuccessListener {
                Toast.makeText(getApplication(), "Successfully added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), "Failed to add topic", Toast.LENGTH_SHORT).show()
            }
    }

    fun addNewSetToFirebase(categoryId: String, topicId: String, setNumberString: String) {
        if (setNumberString.isEmpty()) {
            Toast.makeText(getApplication(), "Invalid Set Number!! try again", Toast.LENGTH_SHORT).show()
            return
        }

        val set = hashMapOf(Constants.SET_NUMBER_FIELD to setNumberString.toInt())

        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId)
            .collection(Constants.TOPIC_COLLECTION).document(topicId)
            .collection(Constants.SET_COLLECTION)
            .add(set)
            .addOnSuccessListener {
                Toast.makeText(getApplication(), "Successfully added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), "Failed to add set", Toast.LENGTH_SHORT).show()
            }
    }

    fun addQuestionToFirebase(categoryId: String, topicId: String, setId: String): Boolean {

        if (!isValidQuestion()) {
            return false
        }

        val question = hashMapOf(
            Constants.QUESTION_QUESTION_FIELD to question.value!!.trim(),
            Constants.QUESTION_OPTION_1_FIELD to option1.value!!.trim(),
            Constants.QUESTION_OPTION_2_FIELD to option2.value!!.trim(),
            Constants.QUESTION_OPTION_3_FIELD to option3.value!!.trim(),
            Constants.QUESTION_OPTION_4_FIELD to option4.value!!.trim(),
            Constants.QUESTION_CORRECT_ANSWER_FIELD to correctAnswer.value!!
        )

        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId)
            .collection(Constants.TOPIC_COLLECTION).document(topicId)
            .collection(Constants.SET_COLLECTION).document(setId)
            .collection(Constants.QUESTION_COLLECTION)
            .add(question)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(getApplication(), "Successfully added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(getApplication(), "Failed to add question", Toast.LENGTH_SHORT).show()
            }

        return true
    }

    private fun isValidQuestion(): Boolean {
        if (question.value == null || question.value!!.trim().isEmpty()){
            Toast.makeText(getApplication(), "Enter valid question", Toast.LENGTH_SHORT).show()
            return false
        }else if (option1.value == null || option1.value!!.trim().isEmpty()){
            Toast.makeText(getApplication(), "Enter valid option1", Toast.LENGTH_SHORT).show()
            return false
        }else if (option2.value == null || option2.value!!.trim().isEmpty()){
            Toast.makeText(getApplication(), "Enter valid option2", Toast.LENGTH_SHORT).show()
            return false
        }else if (option3.value == null || option3.value!!.trim().isEmpty()){
            Toast.makeText(getApplication(), "Enter valid option3", Toast.LENGTH_SHORT).show()
            return false
        }else if (option4.value == null || option4.value!!.trim().isEmpty()){
            Toast.makeText(getApplication(), "Enter valid option4", Toast.LENGTH_SHORT).show()
            return false
        }else{
            return true
        }
    }

    fun addQuestionFinished() {
        question.value = ""
        option1.value = ""
        option2.value = ""
        option3.value = ""
        option4.value = ""
        correctAnswer.value = 0
    }

}
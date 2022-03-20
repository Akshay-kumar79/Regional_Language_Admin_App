package ashutosh.jharkhand.regionallanguageadminapp.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.jharkhand.regionallanguageadminapp.models.Category
import ashutosh.jharkhand.regionallanguageadminapp.utils.Constants
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

    val categoryName = MutableLiveData<String>()

    init {
        getRealtimeCategories()
    }

    private fun getRealtimeCategories() {
        db.collection(Constants.CATEGORIES_COLLECTION).addSnapshotListener { snapshot, error ->
            if (error != null){
                Toast.makeText(getApplication(), error.message, Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val documents = snapshot.documents
                val newCategories = ArrayList<Category>()
                for (document in documents){
                    newCategories.add(Category(document.id,
                        document.getString(Constants.CATEGORIES_NAME_FIELD)?:"",
                        document.getString(Constants.CATEGORIES_IMAGE_FIELD)?:""))
                }
                _categories.value = newCategories
            }
        }
    }

    fun onIconSelected(encodedImage: String) {
        _selectedImageInAddCategory.value = encodedImage
    }

    fun addCategoryFinished() {
        categoryName.value = ""
        _selectedImageInAddCategory.value = ""
    }

    fun addCategoryToFirebase(): Boolean {

        if (!isValidateCategory()) {
            return false
        }

        val category = hashMapOf(
            Constants.CATEGORIES_NAME_FIELD to categoryName.value!!.trim(),
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

    private fun isValidateCategory(): Boolean {
        if (categoryName.value == null || categoryName.value!!.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Enter valid category name", Toast.LENGTH_SHORT).show()
            return false
        } else if (selectedImageInAddCategory.value == null || selectedImageInAddCategory.value!!.isEmpty()) {
            Toast.makeText(getApplication(), "please select icon", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

}
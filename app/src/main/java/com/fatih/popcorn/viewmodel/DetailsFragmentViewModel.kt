package com.fatih.popcorn.viewmodel

import androidx.lifecycle.*
import com.fatih.popcorn.entities.remote.DetailResponse
import com.fatih.popcorn.other.Resource
import com.fatih.popcorn.repository.PopcornRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DetailsFragmentViewModel @Inject constructor(private val popcornRepo:PopcornRepositoryInterface):ViewModel() {

    private var _detailResponse=MutableLiveData<Resource<DetailResponse>>()
    val detailResponse:LiveData<Resource<DetailResponse>>
    get() = _detailResponse

    fun getDetails(searchName:String,id:Int,language:String): LiveData<Resource<DetailResponse>> {

       _detailResponse= popcornRepo.getDetails(searchName,id, language).onStart {
           emit(Resource.loading(null))
       }.asLiveData(viewModelScope.coroutineContext) as MutableLiveData<Resource<DetailResponse>>

        return detailResponse
    }

}
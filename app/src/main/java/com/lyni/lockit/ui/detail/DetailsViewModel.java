package com.lyni.lockit.ui.detail;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lyni.lockit.model.entity.record.Account;
import com.lyni.lockit.model.entity.record.Record;
import com.lyni.lockit.repository.Repository;

/**
 * @author Liangyong Ni
 * description ViewModel
 * @date 2021/6/13
 */
public class DetailsViewModel extends ViewModel {
    private final MutableLiveData<Account> account = new MutableLiveData<>();
    private LiveData<Record> record;
    private Fragment fragment;

    public DetailsViewModel() {

    }

    public void setId(String id) {
        record = Repository.getRecordById(id);
        record.observe(fragment.getViewLifecycleOwner(), record -> account.setValue(record.getAccount()));
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public LiveData<Record> getRecord() {
        return record;
    }

    public LiveData<Account> getAccount() {
        return account;
    }
}
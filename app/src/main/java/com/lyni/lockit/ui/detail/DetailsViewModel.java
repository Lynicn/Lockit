package com.lyni.lockit.ui.detail;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lyni.lockit.model.entity.record.Account;
import com.lyni.lockit.model.entity.record.Record;
import com.lyni.lockit.repository.Repository;

import java.util.List;

/**
 * @author Liangyong Ni
 * description ViewModel
 * @date 2021/6/13
 */
public class DetailsViewModel extends ViewModel {
    private final LiveData<Record> record;
    private final MutableLiveData<List<Account>> accounts = new MutableLiveData<>();
    private final MutableLiveData<List<Account>> loginWays = new MutableLiveData<>();
    private Long id;
    private Fragment fragment;

    public DetailsViewModel() {
        record = Repository.getRecordById(id);
        record.observe(fragment.getViewLifecycleOwner(), record -> {
            accounts.setValue(Repository.findAccountsByIds(record.getAccounts()));
            loginWays.setValue(Repository.findAccountsByIds(record.getLoginWays()));
        });
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public LiveData<Record> getRecord() {
        return record;
    }

    public LiveData<List<Account>> getAccounts() {
        return accounts;
    }

    public LiveData<List<Account>> getLoginWays() {
        return loginWays;
    }
}
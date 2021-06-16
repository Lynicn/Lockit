package com.lyni.lockit.ui.summary;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lyni.lockit.model.entity.record.Account;
import com.lyni.lockit.model.entity.record.Record;
import com.lyni.lockit.repository.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Liangyong Ni
 * description 首页对应的ViewModel
 * @date 2021/6/13
 */
public class SummaryViewModel extends ViewModel {
    private final LiveData<List<Record>> records;

    public SummaryViewModel() {
        records = Repository.getAllRecordsLive();
    }

    public LiveData<List<Record>> getRecords() {
        return records;
    }

}
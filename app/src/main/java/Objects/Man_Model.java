package Objects;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Man_Model extends ViewModel {
    private  MutableLiveData<Manager> livedata=new MutableLiveData<>();
    public LiveData<Manager> getMan(){

        return livedata;
    }
    public Man_Model(){}

   public void setMan(Manager manager){
        livedata.setValue(manager);

   }}

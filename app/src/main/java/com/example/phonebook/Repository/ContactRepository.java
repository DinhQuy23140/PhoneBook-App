package com.example.phonebook.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.phonebook.API.APIService;
import com.example.phonebook.Client.AddressClient;
import com.example.phonebook.DAO.AddressDAO;
import com.example.phonebook.DAO.ContactDAO;
import com.example.phonebook.DAO.DoBDAO;
import com.example.phonebook.DAO.EmailDAO;
import com.example.phonebook.DAO.FavoriteDAO;
import com.example.phonebook.DAO.MessageDAO;
import com.example.phonebook.DAO.NickNameDAO;
import com.example.phonebook.DAO.PhoneNumberDAO;
import com.example.phonebook.DAO.RecentDAO;
import com.example.phonebook.DAO.SocialDAO;
import com.example.phonebook.DAO.URLDAO;
import com.example.phonebook.Model.Address;
import com.example.phonebook.Model.Contact;
import com.example.phonebook.Model.ContactFull;
import com.example.phonebook.Model.DOB;
import com.example.phonebook.Model.Email;
import com.example.phonebook.Model.Favorite;
import com.example.phonebook.Model.Message;
import com.example.phonebook.Model.NickName;
import com.example.phonebook.Model.PhoneNumber;
import com.example.phonebook.Model.Province;
import com.example.phonebook.Model.Recent;
import com.example.phonebook.Model.Social;
import com.example.phonebook.Model.URL;
import com.example.phonebook.Room.AppDataBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactRepository {
    ContactDAO contactDAO;
    PhoneNumberDAO phoneNumberDAO;
    EmailDAO emailDAO;
    NickNameDAO nickNameDAO;
    URLDAO urldao;
    AddressDAO addressDAO;
    DoBDAO DOBDAO;
    SocialDAO socialDAO;
    MessageDAO messageDAO;
    FavoriteDAO favoriteDAO;
    RecentDAO recentDAO;
    MutableLiveData<List<Province>> listProvince = new MutableLiveData<>();


    public interface CallBack {
        void onSuccess(List<ContactFull> result);

        void onFailure(Exception e);
    }

    public interface CallBackInsert {
        void onSuccess(long id);
    }

    public ContactRepository(Context context) {
        AppDataBase appDataBase = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "ContactDB.db").build();
        this.contactDAO = appDataBase.contactDAO();
        this.phoneNumberDAO = appDataBase.phoneNumberDAO();
        this.emailDAO = appDataBase.emailDAO();
        this.nickNameDAO = appDataBase.nickNameDAO();
        this.urldao = appDataBase.urlDAO();
        this.addressDAO = appDataBase.addressDAO();
        this.DOBDAO = appDataBase.dOBDAO();
        this.socialDAO = appDataBase.socialDAO();
        this.messageDAO = appDataBase.messageDAO();
        this.favoriteDAO = appDataBase.favoriteDAO();
        this.recentDAO = appDataBase.recentDAO();
    }

    public void insertContact(Contact contact, CallBackInsert callBack) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            long id = contactDAO.insertContact(contact);
            callBack.onSuccess(id);
        });
    }

    public void insertPhoneNumber(List<PhoneNumber> phoneNumber) {
        phoneNumberDAO.insertPhoneNumber(phoneNumber);
    }

    public void insertEmail(List<Email> email) {
        emailDAO.insertEmail(email);
    }

    public void insertNickName(List<NickName> nickName) {
        nickNameDAO.insertNickName(nickName);
    }

    public void insertURL(List<URL> url) {
        urldao.insertURL(url);
    }

    public void insertAddress(List<Address> address) {
        addressDAO.insertAddress(address);
    }

    public void insertDoB(List<DOB> DoB) {
        DOBDAO.insertDoB(DoB);
    }

    public void insertSocial(List<Social> social) {
        socialDAO.insertSocial(social);
    }

    public void insertMessage(List<Message> message) {
        messageDAO.insertMessage(message);
    }

    public void setFavorite(Favorite favorite) {
        favoriteDAO.insertFavorite(favorite);
    }

    public void updateFavorite(Favorite favorite) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            favoriteDAO.updateFavorite(favorite.isFavorite(), favorite.getContactId());
        });
    }

    public MutableLiveData<List<Province>> getListProvince() {
        return listProvince;
    }

    public void getAllContacts(CallBack callBack) {
        List<Contact> result = new ArrayList<>();
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            callBack.onSuccess(contactDAO.getAllContacts());
        });
    }

    public void getFavoriteContacts(CallBack callBack) {
        List<Contact> result = new ArrayList<>();
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            callBack.onSuccess(favoriteDAO.getFavorite());
        });
    };

    public void getAddress() {
        APIService apiService = AddressClient.getRetrofit().create(APIService.class);
        Call<List<Province>> call = apiService.getProvinces(3);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                listProvince.setValue(response.body());
                Log.d("success", "SUCCESS");
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable throwable) {
                listProvince.setValue(new ArrayList<>());
                Log.d("error", throwable.getMessage());
            }
        });
    }

    public void updateContact(Contact contact) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            contactDAO.updateContact(contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getCompany(), contact.getNote());
        });
    }

    public void updatePhoneNumber(List<PhoneNumber> listPhoneNumber) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            phoneNumberDAO.deletePhoneNumber(listPhoneNumber.get(0).getContactId());
            phoneNumberDAO.insertPhoneNumber(listPhoneNumber);
        });
    }

    public void updateEmail(List<Email> listEmail) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            emailDAO.deleteEmail(listEmail.get(0).getContactId());
            emailDAO.insertEmail(listEmail);
        });
    }

    public void updateNickName(List<NickName> listNickName) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            nickNameDAO.deleteNickName(listNickName.get(0).getContactId());
            nickNameDAO.insertNickName(listNickName);
        });
    }

    public void updateURL(List<URL> listURL) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            urldao.deleteURL(listURL.get(0).getContactId());
            urldao.insertURL(listURL);
        });
    }

    public void updateAddress(List<Address> listAddress) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            addressDAO.deleteAddress(listAddress.get(0).getContactId());
            addressDAO.insertAddress(listAddress);
        });
    }

    public void updateDoB(List<DOB> listDoB) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            DOBDAO.deleteDoB(listDoB.get(0).getContactId());
            DOBDAO.insertDoB(listDoB);
        });
    }

    public void updateSocial(List<Social> listSocial) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            socialDAO.deleteSocial(listSocial.get(0).getContactId());
            socialDAO.insertSocial(listSocial);
        });
    }

    public void updateMessage(List<Message> listMessage) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            messageDAO.deleteMessage(listMessage.get(0).getContactId());
            messageDAO.insertMessage(listMessage);
        });
    }
}

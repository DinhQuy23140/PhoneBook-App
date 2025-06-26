package com.example.phonebook.Room;
import androidx.room.Database;
import androidx.room.RoomDatabase;

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
import com.example.phonebook.Model.DOB;
import com.example.phonebook.Model.Email;
import com.example.phonebook.Model.Favorite;
import com.example.phonebook.Model.Message;
import com.example.phonebook.Model.NickName;
import com.example.phonebook.Model.PhoneNumber;
import com.example.phonebook.Model.Recent;
import com.example.phonebook.Model.Social;
import com.example.phonebook.Model.URL;

@Database(entities = {Contact.class, PhoneNumber.class, Email.class, NickName.class, URL.class, Address.class, DOB.class, Social.class, Message.class, Favorite.class, Recent.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ContactDAO contactDAO();
    public abstract PhoneNumberDAO phoneNumberDAO();
    public abstract EmailDAO emailDAO();
    public abstract NickNameDAO nickNameDAO();
    public abstract URLDAO urlDAO();
    public abstract AddressDAO addressDAO();
    public abstract DoBDAO dOBDAO();
    public abstract SocialDAO socialDAO();
    public abstract MessageDAO messageDAO();
    public abstract FavoriteDAO favoriteDAO();
    public abstract RecentDAO recentDAO();
}

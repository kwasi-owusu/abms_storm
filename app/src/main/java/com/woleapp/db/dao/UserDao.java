/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.woleapp.db.dao;



import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.woleapp.model.User;


import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface UserDao
{
    @Query("select * from users ORDER BY user_id ASC") //WHERE is_deleted = 0
    LiveData<List<User>> loadAll();


    @Query("select * from users WHERE  user_type = :type AND name LIKE :key") //WHERE is_deleted = 0
    List<User> searchForUser(int type, String key);

    //select password from users where email = ("select * from users where email = :email)
    //select * from users where email = ("select email from users where email = :email) AND password = :password

//    @Query("select email from users where email = :email")
//    User getUser(String email);

    @Query("select COUNT(*) from users where email = :email")
    LiveData<Integer> doesUserExist(String email);

    @Query("select * from users where email = :email AND password = :password")
    LiveData<User> getUser(String email, String password);

    @Query("select COUNT(*) from users")//COUNT(id)
    Integer getUserCount();

//    @Query("select * from user where name = :firstName and lastName = :lastName")
//    List<User> findUserByNameAndLastName(String firstName, String lastName);

    @Insert(onConflict = IGNORE)//IGNORE
    Long insertUser(User user);

    @Query("UPDATE users SET is_verified = :isVerified  WHERE user_id = :id")
    int updateUser(Long id,boolean isVerified);

    @Query("UPDATE users SET availableBalance = :wallet_amount  WHERE user_id = :id")
    int updateWalletAmount(Long id,String wallet_amount);

    @Update(onConflict = REPLACE)
    int updateUser(User user);

    @Delete
    int deleteInvoice(User user);

//    @Query("delete from user where name like :badName OR lastName like :badName")
//    int deleteUsersByName(String badName);

//    @Insert(onConflict = IGNORE)
//    void insertOrReplaceUsers(User... users);
//
    @Delete
    void deleteUser(User user);

//    @Query("SELECT * FROM User WHERE :age == :age") // TODO: Fix this!
//    List<User> findUsersYoungerThan(int age);
//
//    @Query("SELECT * FROM User WHERE age < :age")
//    List<User> findUsersYoungerThanSolution(int age);

    @Query("DELETE FROM users")
    void deleteAll();


    /***************** SYNC *****************/

//    @Query("UPDATE invoice SET is_sync_pending= :is_sync_pending AND is_deleted= :is_deleted WHERE id = :id")
//    int updateInvoice(int id, int is_sync_pending,int is_deleted);

//    @Query("DELETE FROM invoice WHERE id = :id AND is_deleted = 1")//DELETE FROM invoice WHERE id = :id AND is_deleted = 1
//    int deleteMarkedInvoice(int id);

    @Query("DELETE FROM users WHERE user_id = :id ")//DELETE FROM invoice WHERE id = :id AND is_deleted = 1
    int deleteUser(int id);


    @Query("select MAX(user_id) from users")
    LiveData<Integer> getMaxID();



}
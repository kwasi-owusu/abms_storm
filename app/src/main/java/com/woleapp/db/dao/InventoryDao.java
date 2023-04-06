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
import androidx.paging.DataSource;
import androidx.room.*;
import com.woleapp.model.Inventory;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface InventoryDao
{
    @Query("select * from inventory") //WHERE is_deleted = 0
    LiveData<List<Inventory>> getUserInventories();

    /*@Query("select * from inventory WHERE product_name LIKE :key") //WHERE is_deleted = 0
    List<User> searchForUser(Long id, String key);*/

    @Query("SELECT inventory.* FROM inventory JOIN inventoryFts ON (inventory.rowid = inventoryFts.rowid) "
            + "WHERE inventoryFts MATCH :query")
    LiveData<List<Inventory>> searchInventory(String query);


    @Transaction
    @Query("SELECT inventory.* FROM inventory JOIN inventoryFts ON (inventory.rowid = inventoryFts.rowid) "
            + "WHERE inventoryFts MATCH :query")//AND inventoryFts MATCH :query
    DataSource.Factory<Integer, Inventory> searchInventoryOnline(String query);

    @Transaction
    @Query("SELECT inventory.* FROM inventory " +
            "JOIN inventoryFts ON (inventory.rowid = inventoryFts.rowid) "
            + "WHERE inventoryFts.product_name LIKE :query")//AND inventoryFts MATCH :query
    DataSource.Factory<Integer, Inventory> searchInventoryOnline1(String query);


    @Query("UPDATE inventory SET image_path =  :image_path  WHERE image_path LIKE :query")
    int updateImagePath(String image_path, String query);

    @Query("UPDATE inventory SET rowid = :new_id WHERE rowid = :old_id") //
    int updateInv(Integer old_id, Integer new_id);//String image_path

    @Query("INSERT INTO inventoryFts(inventoryFts) VALUES ('rebuild')")
    void updateInventoriesFTSTable();


    @Query("select COUNT(*) from inventory")
    LiveData<Integer> getUserInventoryCount();

    @Insert(onConflict = REPLACE)//IGNORE
    Long insertInventory(Inventory inventory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertAll(List<Inventory> inventories);

    @Update(onConflict = REPLACE)
    int updateInventory(Inventory inventory);

    @Delete
    int deleteInventory(Inventory inventory);

    @Query("DELETE FROM inventory")
    void deleteAll();


    /***************** SYNC *****************/

    @Query("DELETE FROM inventory WHERE rowid = :id ")//DELETE FROM invoice WHERE id = :id AND is_deleted = 1
    int deleteInventories(int id);//Long[]

    @Query("select MAX(rowid) from inventory")
    LiveData<Integer> getMaxID();

    @Query("select COUNT(*) from inventory")//COUNT(id)
    Integer getRowCount();

    @Query("select COUNT(*) from inventoryFts")//COUNT(id)
    Integer getRowCountFTS();

}
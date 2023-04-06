package com.woleapp.db;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrations
{
    //    static final Migration MIGRATION_3_4 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
//                    + "`name` TEXT, PRIMARY KEY(`id`))");
//        }
//    };


    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {

            /*You can only add one column at a time. Split it into two ALTER TABLE statements and you should be fine.*/

            database.execSQL("ALTER TABLE users "
                    + " ADD COLUMN isQRRegistered INTEGER DEFAULT 0");
            /*database.execSQL("ALTER TABLE users "
                    + " ADD COLUMN state TEXT");

            database.execSQL("CREATE TABLE IF NOT EXISTS `business_info` (`user_id` INTEGER, `business_name` TEXT,`business_address` TEXT, `phone` TEXT,`password` TEXT,`business_state` TEXT, PRIMARY KEY(`user_id`))");*/
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {

            /*You can only add one column at a time. Split it into two ALTER TABLE statements and you should be fine.*/

            database.execSQL("ALTER TABLE users "
                    + " ADD COLUMN state TEXT");

            database.execSQL("CREATE TABLE IF NOT EXISTS `business_info` (`user_id` INTEGER, `business_name` TEXT,`business_address` TEXT, `phone` TEXT,`password` TEXT,`business_state` TEXT, PRIMARY KEY(`user_id`))");
        }
    };

    static final Migration MIGRATION_5_6 = new Migration(5, 6) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database)
        {
            database.execSQL("CREATE TABLE IF NOT EXISTS `inventory` (`rowid` INTEGER NOT NULL,`user_id` INTEGER NOT NULL, `product_name` TEXT, `category_name` TEXT,`price` REAL, `quantity` INTEGER,`color_name` TEXT,`color_code` TEXT,`description` TEXT,`image_path` TEXT,`insertion_time` INTEGER, PRIMARY KEY(`rowid`))");
            //`user_id` INTEGER NOT NULL
            database.execSQL("CREATE INDEX index_inventory_user_id ON inventory (user_id)");
            database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `inventoryFts` USING FTS4("
                    + "`product_name` TEXT, content=`inventory`)");

            database.execSQL("INSERT INTO inventoryFts (`rowid`, `product_name`) "
                    + "SELECT `rowid`, `product_name` FROM inventory");

        }
    };

    static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {

            /*You can only add one column at a time. Split it into two ALTER TABLE statements and you should be fine.*/

            database.execSQL("ALTER TABLE inventory "
                    + " ADD COLUMN price TEXT");
            /*database.execSQL("ALTER TABLE users "
                    + " ADD COLUMN state TEXT");

            database.execSQL("CREATE TABLE IF NOT EXISTS `business_info` (`user_id` INTEGER, `business_name` TEXT,`business_address` TEXT, `phone` TEXT,`password` TEXT,`business_state` TEXT, PRIMARY KEY(`user_id`))");*/
        }
    };

    static final Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {

            /*You can only add one column at a time. Split it into two ALTER TABLE statements and you should be fine.*/

            /*Because you cannot Alter a view. You have to drop it first and create another one */

            database.execSQL("DROP TABLE inventoryFts");
            database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `inventoryFts` USING FTS4("
                    + "`user_id` INTEGER NOT NULL,`product_name` TEXT, content=`inventory`)");

            /*database.execSQL("ALTER TABLE inventoryFts "
                    + " ADD COLUMN `user_id` INTEGER");*/

            /*database.execSQL("ALTER TABLE users "
                    + " ADD COLUMN state TEXT");

            database.execSQL("CREATE TABLE IF NOT EXISTS `business_info` (`user_id` INTEGER, `business_name` TEXT,`business_address` TEXT, `phone` TEXT,`password` TEXT,`business_state` TEXT, PRIMARY KEY(`user_id`))");*/
        }
    };

    static final Migration MIGRATION_8_9 = new Migration(8, 9) {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {
            database.execSQL("DROP INDEX index_users_email");
        }
    };

    static final Migration MIGRATION_9_10 = new Migration(9, 10) {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {

            /*You can only add one column at a time. Split it into two ALTER TABLE statements and you should be fine.*/

            /*database.execSQL("ALTER TABLE users "
                    + " ADD COLUMN state TEXT");*/

            database.execSQL("CREATE TABLE IF NOT EXISTS `billers` (`id` INTEGER NOT NULL, `service_type` TEXT,`biller_name` TEXT, `biller_code` TEXT,`operation` TEXT,`status` TEXT,`verification_status` TEXT, PRIMARY KEY(`id`))");
        }
    };

    private static final Migration MIGRATION_1_2_Example = new Migration(1, 2)
    {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // create ProjectDimension table
            database.execSQL("CREATE TABLE `ProjectDimension` (`dimensionId` INTEGER, `projectId` INTEGER, " +
                    "`name` TEXT, `value` TEXT, " +
                    "PRIMARY KEY(`dimensionId`), " +
                    "FOREIGN KEY(`projectId`) REFERENCES `Project`(`projectId`) ON DELETE CASCADE)");
        }
    };

    public static final Migration MIGRATION_1_2_Example_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE UserRepo ADD COLUMN user_id INTEGER");
            database.execSQL("ALTER TABLE UserRepo ADD COLUMN login TEXT");
            database.execSQL("CREATE UNIQUE INDEX index_UserRepo_id ON UserRepo (id)");
        }
    };
}

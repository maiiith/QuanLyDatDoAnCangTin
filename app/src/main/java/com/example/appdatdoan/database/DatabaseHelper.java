package com.example.appdatdoan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdatdoan.models.Category;
import com.example.appdatdoan.models.Food;
import com.example.appdatdoan.models.Order;
import com.example.appdatdoan.models.OrderDetail;
import com.example.appdatdoan.models.RevenueStat;
import com.example.appdatdoan.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FoodOrderDB";
    private static final int DATABASE_VERSION = 10; // Version 10 to add price_l to foods and size to order_details

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_FOODS = "foods";
    private static final String TABLE_ORDERS = "orders";
    private static final String TABLE_ORDER_DETAILS = "order_details";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Tables
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT UNIQUE,"
                + "password TEXT,"
                + "fullname TEXT,"
                + "phone TEXT,"
                + "address TEXT,"
                + "role TEXT DEFAULT 'user'" + ")";

        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT,"
                + "image_resource TEXT" + ")";

        String CREATE_FOODS_TABLE = "CREATE TABLE " + TABLE_FOODS + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT,"
                + "category_id INTEGER,"
                + "price REAL,"
                + "price_l REAL,"
                + "description TEXT,"
                + "image_resource TEXT,"
                + "FOREIGN KEY(category_id) REFERENCES " + TABLE_CATEGORIES + "(id)" + ")";

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user_id INTEGER,"
                + "order_date TEXT,"
                + "total_amount REAL,"
                + "status TEXT,"
                + "address TEXT,"
                + "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(id)" + ")";

        String CREATE_ORDER_DETAILS_TABLE = "CREATE TABLE " + TABLE_ORDER_DETAILS + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "order_id INTEGER,"
                + "food_id INTEGER,"
                + "quantity INTEGER,"
                + "price REAL,"
                + "size TEXT,"
                + "FOREIGN KEY(order_id) REFERENCES " + TABLE_ORDERS + "(id),"
                + "FOREIGN KEY(food_id) REFERENCES " + TABLE_FOODS + "(id)" + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_FOODS_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(CREATE_ORDER_DETAILS_TABLE);

        insertDummyData(db);
    }

    private void insertDummyData(SQLiteDatabase db) {
        // Insert Admin User
        db.execSQL("INSERT INTO " + TABLE_USERS + " (username, password, fullname, phone, address, role) VALUES ('admin', 'admin', 'System Admin', '0123456789', 'Admin Address', 'admin')");
        // Insert Default User
        db.execSQL("INSERT INTO " + TABLE_USERS + " (username, password, fullname, phone, address, role) VALUES ('user', 'user', 'Normal User', '0987654321', 'User Address', 'user')");

        // Insert Categories
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " (name, image_resource) VALUES ('Món mặn', 'https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?q=80&w=200&auto=format&fit=crop')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " (name, image_resource) VALUES ('Hoa quả', 'https://images.unsplash.com/photo-1610832958506-aa56368176cf?q=80&w=200&auto=format&fit=crop')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " (name, image_resource) VALUES ('Tráng miệng', 'https://images.unsplash.com/photo-1551024601-bec78aea704b?q=80&w=200&auto=format&fit=crop')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " (name, image_resource) VALUES ('Món chay', 'https://images.unsplash.com/photo-1512621776951-a57141f2eefd?q=80&w=200&auto=format&fit=crop')");

        // Insert Foods
        db.execSQL("INSERT INTO " + TABLE_FOODS + " (name, category_id, price, price_l, description, image_resource) VALUES ('Phở Bò', 1, 45000, 55000, 'Phở bò truyền thống thơm ngon đậm vị cốt xương', 'https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?q=80&w=400&auto=format&fit=crop')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " (name, category_id, price, price_l, description, image_resource) VALUES ('Bánh Mì Thịt', 1, 25000, 35000, 'Bánh mì Việt Nam giòn rụm với thịt nướng', 'https://cdn2.fptshop.com.vn/unsafe/1920x0/filters:format(webp):quality(75)/banh_mi_cuon_thit_nguoi_01_4f4804ebe8.jpg')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " (name, category_id, price, price_l, description, image_resource) VALUES ('Cơm Tấm Sườn', 1, 40000, 50000, 'Cơm tấm đặc sản Sài Gòn kèm sườn nướng mỡ hành', 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=400&auto=format&fit=crop')");
        
        db.execSQL("INSERT INTO " + TABLE_FOODS + " (name, category_id, price, price_l, description, image_resource) VALUES ('Sinh Tố Xoài', 2, 30000, 40000, 'Sinh tố xoài tươi mát xay cùng sữa đặc', 'https://cdn2.fptshop.com.vn/unsafe/1920x0/filters:format(webp):quality(75)/2023_8_12_638274776151301961_cach-lam-sinh-to-xoai.jpg')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " (name, category_id, price, price_l, description, image_resource) VALUES ('Trái Cây Tô', 2, 50000, 65000, 'Trái cây theo mùa thanh mát mọng nước', 'https://images.unsplash.com/photo-1610832958506-aa56368176cf?q=80&w=400&auto=format&fit=crop')");
        
        db.execSQL("INSERT INTO " + TABLE_FOODS + " (name, category_id, price, price_l, description, image_resource) VALUES ('Chè Khúc Bạch', 3, 25000, 35000, 'Chè thanh mát, ngọt dịu với thạch khúc bạch', 'https://cdn2.fptshop.com.vn/unsafe/1920x0/filters:format(webp):quality(75)/2023_9_28_638315335535725712_che-khuc-bach-thumb.jpg')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " (name, category_id, price, price_l, description, image_resource) VALUES ('Bánh Flan', 3, 15000, 20000, 'Bánh flan caramen béo ngậy mềm mịn', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSd1edmnulgmAoqTZWm-2hnVOya1SJOt39YfA&s')");
        
        db.execSQL("INSERT INTO " + TABLE_FOODS + " (name, category_id, price, price_l, description, image_resource) VALUES ('Bún Chay', 4, 30000, 40000, 'Bún nước lèo chay nấu với nấm ngọt thanh', 'https://cdn.tgdd.vn/2021/02/CookProduct/1111-1200x676-3.jpg')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " (name, category_id, price, price_l, description, image_resource) VALUES ('Cơm Chay Thập Cẩm', 4, 35000, 45000, 'Cơm chay đủ món rau củ đậu phụ đầy dinh dưỡng', 'https://storage.googleapis.com/onelife-public/blog.onelife.vn/2021/10/cach-lam-com-chien-thap-cam-chay-mon-chay-698703610057.jpg')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // --- User Operations ---
    public boolean registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("fullname", user.getFullname());
        values.put("phone", user.getPhone());
        values.put("address", user.getAddress());
        values.put("role", user.getRole() != null ? user.getRole() : "user");

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public User loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE username=? AND password=?", new String[]{username, password});
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6) // role
            );
            cursor.close();
            return user;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE id=?", new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            cursor.close();
            return user;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    // --- Category Operations ---
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIES, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // --- Food Operations ---
    public List<Food> getAllFoods() {
        List<Food> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Food(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                        cursor.getDouble(3), cursor.getDouble(4), cursor.getString(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Food> getFoodsByCategory(int categoryId) {
        List<Food> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS + " WHERE category_id=?", new String[]{String.valueOf(categoryId)});
        if (cursor.moveToFirst()) {
            do {
                list.add(new Food(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                        cursor.getDouble(3), cursor.getDouble(4), cursor.getString(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", food.getName());
        values.put("category_id", food.getCategoryId());
        values.put("price", food.getPrice());
        values.put("price_l", food.getPriceL());
        values.put("description", food.getDescription());
        values.put("image_resource", food.getImageResource());

        long result = db.insert(TABLE_FOODS, null, values);
        return result != -1;
    }

    public boolean deleteFood(int foodId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_FOODS, "id=?", new String[]{String.valueOf(foodId)});
        return result != -1;
    }

    public Food getFoodById(int foodId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS + " WHERE id=?", new String[]{String.valueOf(foodId)});
        if (cursor != null && cursor.moveToFirst()) {
            Food food = new Food(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                    cursor.getDouble(3), cursor.getDouble(4), cursor.getString(5), cursor.getString(6));
            cursor.close();
            return food;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public boolean updateFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", food.getName());
        values.put("category_id", food.getCategoryId());
        values.put("price", food.getPrice());
        values.put("price_l", food.getPriceL());
        values.put("description", food.getDescription());
        values.put("image_resource", food.getImageResource());

        long result = db.update(TABLE_FOODS, values, "id=?", new String[]{String.valueOf(food.getId())});
        return result != -1;
    }

    // --- Order Operations ---
    public long createOrder(int userId, double totalAmount, String address, List<OrderDetail> details) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        long orderId = -1;
        try {
            ContentValues orderValues = new ContentValues();
            orderValues.put("user_id", userId);
            orderValues.put("order_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
            orderValues.put("total_amount", totalAmount);
            orderValues.put("status", "Pending");
            orderValues.put("address", address);

            orderId = db.insert(TABLE_ORDERS, null, orderValues);

            if (orderId != -1) {
                for (OrderDetail detail : details) {
                    ContentValues detailValues = new ContentValues();
                    detailValues.put("order_id", orderId);
                    detailValues.put("food_id", detail.getFoodId());
                    detailValues.put("quantity", detail.getQuantity());
                    detailValues.put("price", detail.getPrice());
                    detailValues.put("size", detail.getSize());
                    db.insert(TABLE_ORDER_DETAILS, null, detailValues);
                }
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
        }
        return orderId;
    }

    public List<Order> getOrdersByUser(int userId) {
        List<Order> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDERS + " WHERE user_id=? ORDER BY id DESC", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                list.add(new Order(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getDouble(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDERS + " ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Order(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getDouble(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        int rows = db.update(TABLE_ORDERS, values, "id=?", new String[]{String.valueOf(orderId)});
        return rows > 0;
    }

    public String getOrderSummaryString(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT f.name, od.quantity, od.size FROM " + TABLE_ORDER_DETAILS + " od JOIN " + TABLE_FOODS + " f ON od.food_id = f.id WHERE od.order_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});
        StringBuilder summary = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                int qty = cursor.getInt(1);
                String size = cursor.getString(2);
                summary.append(qty).append("x ").append(name).append(" (Size ").append(size).append(")\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return summary.toString().trim();
    }

    public List<RevenueStat> getMonthlyRevenue() {
        List<RevenueStat> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT strftime('%m/%Y', order_date) as display_month, " +
                       "strftime('%Y-%m', order_date) as sort_month, " +
                       "SUM(total_amount) " +
                       "FROM " + TABLE_ORDERS + " " +
                       "WHERE status='Completed' " +
                       "GROUP BY sort_month ORDER BY sort_month DESC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String displayMonth = cursor.getString(0);
                String sortMonth = cursor.getString(1);
                double total = cursor.getDouble(2);
                RevenueStat stat = new RevenueStat(displayMonth, total);
                stat.setTopFoods(getTopFoodsForMonth(sortMonth));
                list.add(stat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private String getTopFoodsForMonth(String sortMonth) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT f.name, SUM(od.quantity) as total_qty " +
                       "FROM " + TABLE_ORDER_DETAILS + " od " +
                       "JOIN " + TABLE_ORDERS + " o ON od.order_id = o.id " +
                       "JOIN " + TABLE_FOODS + " f ON od.food_id = f.id " +
                       "WHERE strftime('%Y-%m', o.order_date) = ? AND o.status='Completed' " +
                       "GROUP BY f.name " +
                       "ORDER BY total_qty DESC " +
                       "LIMIT 3";
        Cursor cursor = db.rawQuery(query, new String[]{sortMonth});
        StringBuilder sb = new StringBuilder();
        int rank = 1;
        if (cursor.moveToFirst()) {
            do {
                sb.append(rank++).append(". ").append(cursor.getString(0))
                  .append(" (").append(cursor.getInt(1)).append(" phần)\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sb.toString().trim();
    }
}

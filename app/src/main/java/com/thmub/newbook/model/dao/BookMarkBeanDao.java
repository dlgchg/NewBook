package com.thmub.newbook.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.thmub.newbook.bean.BookMarkBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOOK_MARK_BEAN".
*/
public class BookMarkBeanDao extends AbstractDao<BookMarkBean, String> {

    public static final String TABLENAME = "BOOK_MARK_BEAN";

    /**
     * Properties of entity BookMarkBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ChapterLink = new Property(0, String.class, "chapterLink", true, "CHAPTER_LINK");
        public final static Property ChapterTitle = new Property(1, String.class, "chapterTitle", false, "CHAPTER_TITLE");
        public final static Property BookLink = new Property(2, String.class, "bookLink", false, "BOOK_LINK");
        public final static Property BookTitle = new Property(3, String.class, "bookTitle", false, "BOOK_TITLE");
        public final static Property ChapterIndex = new Property(4, int.class, "chapterIndex", false, "CHAPTER_INDEX");
        public final static Property ChapterPage = new Property(5, int.class, "chapterPage", false, "CHAPTER_PAGE");
        public final static Property Content = new Property(6, String.class, "content", false, "CONTENT");
    }


    public BookMarkBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BookMarkBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOK_MARK_BEAN\" (" + //
                "\"CHAPTER_LINK\" TEXT PRIMARY KEY NOT NULL ," + // 0: chapterLink
                "\"CHAPTER_TITLE\" TEXT," + // 1: chapterTitle
                "\"BOOK_LINK\" TEXT," + // 2: bookLink
                "\"BOOK_TITLE\" TEXT," + // 3: bookTitle
                "\"CHAPTER_INDEX\" INTEGER NOT NULL ," + // 4: chapterIndex
                "\"CHAPTER_PAGE\" INTEGER NOT NULL ," + // 5: chapterPage
                "\"CONTENT\" TEXT);"); // 6: content
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOK_MARK_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BookMarkBean entity) {
        stmt.clearBindings();
 
        String chapterLink = entity.getChapterLink();
        if (chapterLink != null) {
            stmt.bindString(1, chapterLink);
        }
 
        String chapterTitle = entity.getChapterTitle();
        if (chapterTitle != null) {
            stmt.bindString(2, chapterTitle);
        }
 
        String bookLink = entity.getBookLink();
        if (bookLink != null) {
            stmt.bindString(3, bookLink);
        }
 
        String bookTitle = entity.getBookTitle();
        if (bookTitle != null) {
            stmt.bindString(4, bookTitle);
        }
        stmt.bindLong(5, entity.getChapterIndex());
        stmt.bindLong(6, entity.getChapterPage());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BookMarkBean entity) {
        stmt.clearBindings();
 
        String chapterLink = entity.getChapterLink();
        if (chapterLink != null) {
            stmt.bindString(1, chapterLink);
        }
 
        String chapterTitle = entity.getChapterTitle();
        if (chapterTitle != null) {
            stmt.bindString(2, chapterTitle);
        }
 
        String bookLink = entity.getBookLink();
        if (bookLink != null) {
            stmt.bindString(3, bookLink);
        }
 
        String bookTitle = entity.getBookTitle();
        if (bookTitle != null) {
            stmt.bindString(4, bookTitle);
        }
        stmt.bindLong(5, entity.getChapterIndex());
        stmt.bindLong(6, entity.getChapterPage());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public BookMarkBean readEntity(Cursor cursor, int offset) {
        BookMarkBean entity = new BookMarkBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // chapterLink
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // chapterTitle
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // bookLink
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // bookTitle
            cursor.getInt(offset + 4), // chapterIndex
            cursor.getInt(offset + 5), // chapterPage
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // content
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BookMarkBean entity, int offset) {
        entity.setChapterLink(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setChapterTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBookLink(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBookTitle(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setChapterIndex(cursor.getInt(offset + 4));
        entity.setChapterPage(cursor.getInt(offset + 5));
        entity.setContent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final String updateKeyAfterInsert(BookMarkBean entity, long rowId) {
        return entity.getChapterLink();
    }
    
    @Override
    public String getKey(BookMarkBean entity) {
        if(entity != null) {
            return entity.getChapterLink();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BookMarkBean entity) {
        return entity.getChapterLink() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

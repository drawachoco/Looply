package com.example.looply

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Creating table to store notes
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_NOTE TEXT NOT NULL
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    // Function to insert a new note into the database
    fun insertNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_DATE, note.date)
            put(COLUMN_NOTE, note.note)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Function to fetch all notes from the database
    fun getAllNotes(): List<Note> {
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        // Iterate over cursor and populate notes list
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val note = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE))

            val notee = Note(id, title, date, note)
            notesList.add(notee)
        }
        cursor.close()
        db.close()
        return notesList
    }

    // Function to fetch a single note by its ID
    fun getNoteByID(noteId: Int): Note? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val note = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE))
            cursor.close()
            db.close()
            return Note(id, title, date, note)
        }
        cursor.close()
        db.close()
        return null
    }

    // Function to update a note in the database
    fun updateNote(note: Note): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_DATE, note.date)
            put(COLUMN_NOTE, note.note)
        }

        val rowsUpdated = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(note.id.toString()))
        db.close()
        return rowsUpdated
    }

    // Function to delete a note by its ID (alternative implementation)
    fun deleteNote(noteId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}

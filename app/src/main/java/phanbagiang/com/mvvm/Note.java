package phanbagiang.com.mvvm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;


    private int color;
    private int text_color;

    @ColumnInfo(name = "priority_column")
    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
//        this.color=0xffffffff;
//        this.text_color=0x00000000;
    }

    public Note() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getText_color() {
        return text_color;
    }

    public void setText_color(int text_color) {
        this.text_color = text_color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}

package phanbagiang.com.mvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import phanbagiang.com.mvvm.Note;
import phanbagiang.com.mvvm.R;

public class NoteAdapter extends ListAdapter<Note,NoteAdapter.ViewHolder> {
    onItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK=new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())&&
                    oldItem.getDescription().equals(newItem.getDescription())&&
                    oldItem.getPriority()==newItem.getPriority()&&
                    oldItem.getColor()==newItem.getColor()&&
                    oldItem.getText_color()==newItem.getText_color();
        }
    };
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note=getItem(position);
        holder.txtTitle.setText(note.getTitle());
        holder.txtDescription.setText(note.getDescription());
        holder.txtPriority.setText(String.valueOf(note.getPriority()));
        holder.cardView.setCardBackgroundColor(note.getColor());

        // change text color
        holder.txtTitle.setTextColor(note.getText_color());
        holder.txtDescription.setTextColor(note.getText_color());
        holder.txtPriority.setTextColor(note.getText_color());
    }

    public interface onItemClickListener{
        void onItemClick(Note note);
    }


    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.listener=onItemClickListener;
    }

    public Note getNoteAt(int position){
        return getItem(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtDescription, txtPriority;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDescription=itemView.findViewById(R.id.text_view_description);
            txtTitle=itemView.findViewById(R.id.text_view_title);
            txtPriority=itemView.findViewById(R.id.text_view_priority);

            cardView=itemView.findViewById(R.id.item_root);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null && getAdapterPosition()!=RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(getAdapterPosition()));
                }
            });
        }
    }
}

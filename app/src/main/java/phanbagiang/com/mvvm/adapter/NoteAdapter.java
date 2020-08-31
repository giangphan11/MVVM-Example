package phanbagiang.com.mvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import phanbagiang.com.mvvm.Note;
import phanbagiang.com.mvvm.R;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> mData;
    private Context mContext;

    public NoteAdapter(Context mContext) {
        this.mContext = mContext;
        mData=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.note_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note=mData.get(position);
        holder.txtTitle.setText(note.getTitle());
        holder.txtDescription.setText(note.getDescription());
        holder.txtPriority.setText(String.valueOf(note.getPriority()));
    }

    public void addNote(List<Note> data){
        mData=data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtDescription, txtPriority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDescription=itemView.findViewById(R.id.text_view_description);
            txtTitle=itemView.findViewById(R.id.text_view_title);
            txtPriority=itemView.findViewById(R.id.text_view_priority);
        }
    }
}

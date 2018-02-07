package fi.tut.miksa.tietokanta;

import android.app.ListActivity;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Random;


//7.2.1018 akubranch
public class MainActivity extends ListActivity{//AppCompatActivity {
    private CommentsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource=new CommentsDataSource(this);
        dataSource.open();

        List<Comment> values=dataSource.getAllComments();
        ArrayAdapter<Comment> adapter=new ArrayAdapter<Comment>(this,android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

    }

    public void onClick(View view){
        ArrayAdapter<Comment> adapter=(ArrayAdapter<Comment>)getListAdapter();
        Comment comment=null;
        switch (view.getId()) {
            case R.id.add:
                String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                comment = dataSource.createComment(comments[nextInt]);
                adapter.add(comment);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(0);
                    dataSource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
    }
    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}

package xmut.ygnn.petstore;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xmut.ygnn.petstore.util.DatabaseUtil;
import xmut.ygnn.petstore.util.http.HttpAddress;
import xmut.ygnn.petstore.video.activity.PlayActivity;
import xmut.ygnn.petstore.video.adapter.SelectPlayDataAdapter;
import xmut.ygnn.petstore.video.contract.OnItemClickListener;
import xmut.ygnn.petstore.video.entity.PlayEntity;
import xmut.ygnn.petstore.video.utils.Constants;
import xmut.ygnn.petstore.video.utils.DataFormatUtil;


public class VideoFragment extends Fragment implements OnItemClickListener, View.OnClickListener {

    //服务端获取的视频对象josn
    public static String urljson;


    private Context context;

    //视频播放列表
    private List<PlayEntity> playEntityList;

    private View view;

    // Play recyclerView
    private RecyclerView playRecyclerView;

    // Input play url
    private EditText addressEt;

    // Play button
    private Button playBt;

    // Load view
    private ProgressBar playLoading;

    // Play adapter
    private SelectPlayDataAdapter selectPlayDataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        playEntityList = new ArrayList<>();
        //Used to get Videos from Cloud database in Control Class
        urljson = DatabaseUtil.selectList(HttpAddress.video(), "list").getResult();
        view = LayoutInflater.from(context).inflate(R.layout.activity_browser_fragment, null);

        initView();
        return view;
    }

    private void initView() {
        playRecyclerView = (RecyclerView) view.findViewById(R.id.player_recycler_view);
        playLoading = (ProgressBar) view.findViewById(R.id.play_loading);
        addressEt = (EditText) view.findViewById(R.id.input_path_ed);
        playBt = (Button) view.findViewById(R.id.main_play_btn);
        playBt.setOnClickListener(this);
        selectPlayDataAdapter = new SelectPlayDataAdapter(context, this);
        playRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        playRecyclerView.setAdapter(selectPlayDataAdapter);
        playRecyclerView.setVisibility(View.GONE);
        playLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateView();
    }

    //获取视频List 并载入Recycleview
    private void updateView() {
        playEntityList.clear();
//        playEntityList.addAll(DataFormatUtil.getPlayList(context));
        playEntityList.addAll(DataFormatUtil.getPlayList(context, urljson));
        updateRecyclerView(playEntityList);
    }


    public VideoFragment() {
        super();

    }


    @Override
    public void onItemClick(int pos) {
        PlayEntity playEntity = getPlayFromPosition(pos);
        if (playEntity != null) {
            PlayActivity.startPlayActivity(context, playEntity);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_play_btn:
                String inputUrl = getInputUrl();
                if (TextUtils.isEmpty(inputUrl)) {
                    Toast.makeText(context, getResources().getString(R.string.input_path), Toast.LENGTH_SHORT).show();
                } else {
                    PlayActivity.startPlayActivity(context, getInputPlay(inputUrl));
                }
                break;
            default:
                break;
        }
    }

    public void updateRecyclerView(List<PlayEntity> playList) {
        selectPlayDataAdapter.setSelectPlayList(playList);
        playLoading.setVisibility(View.GONE);
        playRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Get input text
     *
     * @return Text value
     */
    public String getInputUrl() {
        if (addressEt.getText() == null) {
            return "";
        } else {
            return addressEt.getText().toString();
        }
    }

    public PlayEntity getInputPlay(String inputUrl) {
        PlayEntity playEntity = new PlayEntity();
        playEntity.setUrl(inputUrl);
        playEntity.setUrlType(Constants.UrlType.URL);
        return playEntity;
    }


    /**
     * If the data is empty
     *
     * @return The data is empty
     */
    private boolean isPlayListEmpty() {
        return playEntityList == null || playEntityList.size() == 0;
    }

    /**
     * The currently selected data is valid
     *
     * @param position Select position
     * @return Effective
     */
    private boolean isPlayEffective(int position) {
        return !isPlayListEmpty() && playEntityList.size() > position;
    }

    /**
     * Data for the selected titles
     *
     * @param position Select position
     * @return Data
     */
    public PlayEntity getPlayFromPosition(int position) {
        if (isPlayEffective(position)) {
            return playEntityList.get(position);
        }
        return null;
    }


}
package neo.vn.test365children.Activity.weeklyExercises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.R;

/**
 * Created 11/03/2022 at 15:56
 * Company: VHM
 */
public class FragmentExercisesDone extends BaseFragment {

    public static FragmentExercisesDone newInstance() {
        FragmentExercisesDone fragment = new FragmentExercisesDone();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exer_current_week, container, false);
        ButterKnife.bind(this, view);
        initEvent();
        return view;
    }

    private void initEvent() {

    }
}

package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.transportapp.lazar.transportapp.R;
import com.transportapp.lazar.transportapp.activities.RequestActivity;

import java.util.List;

import helpers.DateHelper;
import helpers.ValuePairViewHelper;
import model.Request;
import services.GoogleMapsService;
import utils.Constants;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>{

    private Context context;
    private List<Request> requests;
    private GoogleMapsService mapsService;

    public RequestAdapter(Context context, List<Request> requests) {
        this.context = context;
        this.requests = requests;
        this.mapsService = new GoogleMapsService();
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_request,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {
        holder.request = requests.get(position);
        String startLocation = mapsService.getAddressFromLatLng(context, holder.request.getStartLocation().getLat(), holder.request.getStartLocation().getLng());
        String endLocation = mapsService.getAddressFromLatLng(context, holder.request.getEndLocation().getLat(), holder.request.getEndLocation().getLng());

        ValuePairViewHelper.setLabelValuePair(holder.itemView, R.id.start_location, "Start Location: ", startLocation);
        ValuePairViewHelper.setLabelValuePair(holder.itemView, R.id.end_location, "End Location: ", endLocation);
        ValuePairViewHelper.setLabelValuePair(holder.itemView, R.id.start_date, "Start Date: ", DateHelper.dateToString(holder.request.getStartDate()));
        ValuePairViewHelper.setLabelValuePair(holder.itemView, R.id.end_date, "End Date: ", DateHelper.dateToString(holder.request.getEndDate()));
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Request request;
//        private TextView startLocation;
//        private TextView endLocation;
//        private TextView startDate;
//        private TextView endDate;

        public ViewHolder(View itemView) {
            super(itemView);

//            startLocation = itemView.findViewById(R.id.start_location);
//            endLocation = itemView.findViewById(R.id.end_location);
//            startDate = itemView.findViewById(R.id.start_date);
//            endDate = itemView.findViewById(R.id.end_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RequestActivity.class);

                    intent.putExtra("id", request.getId());
                    intent.putExtra("startLocation", request.getStartLocation().getLat() + Constants.SEPARATOR + request.getStartLocation().getLng());
                    intent.putExtra("endLocation", request.getEndLocation().getLat() + Constants.SEPARATOR + request.getEndLocation().getLng());
                    intent.putExtra("startDate", request.getStartDate().getTime());
                    intent.putExtra("endDate", request.getEndDate().getTime());
                    intent.putExtra("price", request.getPrice());
                    intent.putExtra("discount", request.getDiscount());
                    intent.putExtra("status", request.getStatus());
                    intent.putExtra("destinationId", request.getDestinationId());
                    intent.putExtra("submissionDate", request.getSubmissionDate().getTime());
                    intent.putExtra("confirmationRequestDate", request.getConfirmationRequestDate().getTime());
                    intent.putExtra("userId", request.getUserId());
                    intent.putExtra("destinationOrder", request.getDestinationOrder());
                    intent.putExtra("distance", request.getDistance());

                    context.startActivity(intent);
                }
            });
        }

    }
}

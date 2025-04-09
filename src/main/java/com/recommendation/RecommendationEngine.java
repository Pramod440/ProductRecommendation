package com.recommendation;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.net.URL;
import java.util.List;

public class RecommendationEngine {

    public static void main(String[] args) throws Exception {
        // Step 1: Load the dataset
        URL resource = RecommendationEngine.class.getClassLoader().getResource("dataset.csv");
        if (resource == null) {
            throw new IllegalArgumentException("File not found: dataset.csv");
        }
        File file = new File(resource.toURI());
        DataModel model = new FileDataModel(file);
        System.out.println(" DataModel loaded successfully!");

        // Step 2: Create a similarity metric
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        // Step 3: Define neighborhood (e.g., 2 nearest users)
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

        // Step 4: Build the recommender
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        // Step 5: Get recommendations for a user (e.g., user with ID 1)
        int userId = 1;
        List<RecommendedItem> recommendations = recommender.recommend(userId, 3); // Recommend top 3 items

        // Step 6: Display the recommendations
        System.out.println(" Recommended items for user " + userId + ":");
        for (RecommendedItem item : recommendations) {
            System.out.println(" Item ID: " + item.getItemID() + ", Score: " + item.getValue());
        }
    }
}

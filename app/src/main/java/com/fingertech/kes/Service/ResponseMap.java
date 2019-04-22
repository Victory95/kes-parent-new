package com.fingertech.kes.Service;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseMap implements Serializable {

    public class Predictions{
        @SerializedName("status")
        private String status;

        @SerializedName("predictions")
        private List<Prediction> predictions;

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<Prediction> getPredictions() {
            return this.predictions;
        }

        public void setPredictions(List<Prediction> predictions) {
            this.predictions = predictions;
        }
    }

    public class Prediction{
        @SerializedName("description")
        private String description;

        @SerializedName("id")
        private String id;

        @SerializedName("matched_substrings")
        private List matchedSubstrings;

        @SerializedName("place_id")
        private String placeId;

        @SerializedName("reference")
        private String reference;

        @SerializedName("terms")
        private List<Term> terms;

        @SerializedName("types")
        private List<String> types;

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<MatchedSubstring> getMatchedSubstrings() {
            return this.matchedSubstrings;
        }

        public void setMatchedSubstrings(List<MatchedSubstring> matchedSubstrings) {
            this.matchedSubstrings = matchedSubstrings;
        }

        public String getPlaceId() {
            return this.placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public String getReference() {
            return this.reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public List<Term> getTerms() {
            return this.terms;
        }

        public void setTerms(List<Term> terms) {
            this.terms = terms;
        }

        public List<String> getTypes() {
            return this.types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }
    public class MatchedSubstring{
        @SerializedName("length")
        private int length;

        @SerializedName("offset")
        private int offset;

        public int getLength() {
            return this.length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getOffset() {
            return this.offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }
    }
    public class Term{
        @SerializedName("offset")
        private int offset;

        @SerializedName("value")
        private String value;

        public int getOffset() {
            return this.offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}

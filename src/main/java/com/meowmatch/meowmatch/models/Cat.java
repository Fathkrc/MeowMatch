package com.meowmatch.meowmatch.models;
import com.meowmatch.meowmatch.models.enums.Gender;
import org.springframework.data.annotation.Id;

public class Cat{
        @Id
        private String id;
        private String name;
        private int age;
        private Gender gender;
        private String location;
        private String imageUrl;
        private String breed;
        private String bio;

        public Cat(String name, int age, Gender gender, String location,
                   String imageUrl, String breed, String bio) {
                this.name = name;
                this.age = age;
                this.gender = gender;
                this.location = location;
                this.imageUrl = imageUrl;
                this.breed = breed;
                this.bio = bio;
        }

        // Empty const
        public Cat() {}

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        public Gender getGender() { return gender; }
        public void setGender(Gender gender) { this.gender = gender; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

        public String getBreed() { return breed; }
        public void setBreed(String breed) { this.breed = breed; }

        public String getBio() { return bio; }
        public void setBio(String bio) { this.bio = bio; }

        // toString() for debugging
        @Override
        public String toString() {
                return "Cat{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", age=" + age +
                        ", gender=" + gender +
                        ", location='" + location + '\'' +
                        ", breed='" + breed + '\'' +
                        '}';
        }
}
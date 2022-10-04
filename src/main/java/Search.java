public class Search {
    private boolean search(int[] numbers, int x){
        int startIndex = 0;
        int endIndex = numbers.length;
        while(startIndex<=endIndex){
            int midIndex = startIndex + (endIndex - startIndex)/2;
            System.out.println(midIndex);
            if(x==numbers[midIndex]){
                return true;
            } else if (x < numbers[midIndex]) {
                startIndex = midIndex + 1;
            }
            else {
                endIndex = midIndex - 1;
            }
        }
        return false;
    }
}



public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> res = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            res.addLast(word.charAt(i));
        }
//        res.printDeque();
        return res;
    }

    public boolean isPalindrome(String word){
        Deque<Character> list = wordToDeque(word);
        return helper(list);
    }
    public boolean helper(Deque<Character> list){
        if (list.isEmpty() ||list.size()==1){
            return true;
        }
        if (list.removeFirst().equals(list.removeLast())){
            helper(list);
        }else {
            return false;
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> list = wordToDeque(word);
        int len = list.size()/2;
        for (int i = 0;i < len;i++){
//            System.out.println(list.get(i));
            if (!cc.equalChars(list.get(i),list.get(list.size()-i-1))){
                return false;
            }
        }
        return true;
    }
}

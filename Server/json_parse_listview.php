<?php   
           $connect = mysqli_connect("", "", "", "");  
           $sql = "SELECT * FROM signup";  
           $result = mysqli_query($connect, $sql);  
           $json_array = array();  
           while($row = mysqli_fetch_assoc($result))  
           {  
                $json_array[] = $row;  
           } 
           //echo '<pre>';
            
           
           print_r(json_encode($json_array));  
           
           
          // echo '</pre>';
           //echo json_encode($json_array);  
           ?>  
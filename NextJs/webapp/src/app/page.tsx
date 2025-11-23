import {redirect} from 'next/navigation'

/* su dung redirect trong nextjs de chuyen huong trang sang trang client khi hien thi dau tien */
export default function Home(){
  redirect('/client');
}
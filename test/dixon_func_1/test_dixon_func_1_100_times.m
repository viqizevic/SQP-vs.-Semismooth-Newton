function test_dixon_func_1_100_times(show)
    t1_total = 0;
    t2_total = 0;
    it1_total = 0;
    it2_total = 0;
    total = 100;
    for k=1:total
        [x_ssn,it_ssn,t_ssn,x_sqp,it_sqp,t_sqp] = test_dixon_func_1(0);
        if (norm(x_ssn-x_sqp) > 0.1)
            break;
        end
        if (t_ssn > 0.5 || t_sqp > 0.5)
            break;
        end
        t1_total = t1_total + t_ssn;
        t2_total = t2_total + t_sqp;
        it1_total = it1_total + it_ssn;
        it2_total = it2_total + it_sqp;
    end
    t1_total = t1_total/total;
    t2_total = t2_total/total;
    it1_total = it1_total/total;
    it2_total = it2_total/total;
    if ( show == 1 )
        x1 = sprintf('%.3f ',x_ssn);
        t1 = sprintf('solved in %.2f ms.',t1_total*1000);
        str1 = ['x_ssn = [ ', x1, '], it = ', num2str(it1_total), ', ', t1];
        x2 = sprintf('%.3f ',x_sqp);
        t2 = sprintf('solved in %.2f ms.',t2_total*1000);
        str2 = ['x_sqp = [ ', x2, '], it = ', num2str(it2_total), ', ', t2];
        disp(str1);
        disp(str2);
    end
end

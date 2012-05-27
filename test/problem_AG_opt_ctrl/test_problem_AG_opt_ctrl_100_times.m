function test_problem_AG_opt_ctrl_100_times(show)
    t1_total = 0;
    t2_total = 0;
    t_max = 0.95;
    t1_min = t_max;
    t2_min = t_max;
    t1_max = 0;
    t2_max = 0;
    it1_total = 0;
    it2_total = 0;
    total = 100;
    k = 1;
    k_total = 0;
    while k<=total
        k_total = k_total+1;
        [x_ssn,it_ssn,t_ssn,x_sqp,it_sqp,t_sqp] = test_problem_AG_opt_ctrl(0);
        if (norm(x_ssn-x_sqp) > 0.1)
            break;
        end
        if (t_ssn > t_max || t_sqp > t_max)
            break;
        end
        if (t_ssn == 0 || t_sqp == 0)
            continue;
        end
        k = k+1;
        t1_total = t1_total + t_ssn;
        t2_total = t2_total + t_sqp;
        if (t1_min > t_ssn)
            t1_min = t_ssn;
        end
        if (t2_min > t_sqp)
            t2_min = t_sqp;
        end
        if (t1_max < t_ssn)
            t1_max = t_ssn;
        end
        if (t2_max < t_sqp)
            t2_max = t_sqp;
        end
        it1_total = it1_total + it_ssn;
        it2_total = it2_total + it_sqp;
    end
    t1_total = t1_total/total;
    t2_total = t2_total/total;
    it1_total = it1_total/total;
    it2_total = it2_total/total;
    if ( nargin == 0 )
        show = 1;
    end
    if ( (show == 1) && (k == total+1) )
        x1 = sprintf('%.3f ',x_ssn);
        t1 = sprintf('solved in %.2f ms',t1_total*1000);
        t1min = sprintf(' (%.2f -',t1_min*1000);
        t1max = sprintf(' %.2f).',t1_max*1000);
        str1 = ['x_ssn = [ ', x1, '], it = ', num2str(it1_total), ', ', t1, t1min, t1max];
        x2 = sprintf('%.3f ',x_sqp);
        t2 = sprintf('solved in %.2f ms',t2_total*1000);
        t2min = sprintf(' (%.2f -',t2_min*1000);
        t2max = sprintf(' %.2f).',t2_max*1000);
        str2 = ['x_sqp = [ ', x2, '], it = ', num2str(it2_total), ', ', t2, t2min, t2max];
        disp(str1);
        disp(str2);
        if (k_total ~= 100)
            disp(['[', num2str(k_total), ']']);
        end
    end
end

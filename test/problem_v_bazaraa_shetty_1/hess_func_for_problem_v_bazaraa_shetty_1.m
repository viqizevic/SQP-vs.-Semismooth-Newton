function H = hess_func_for_problem_v_bazaraa_shetty_1(x)
	H = [ 12*(x(1)-2)^2+2  -4;
               -4           8 ];
end
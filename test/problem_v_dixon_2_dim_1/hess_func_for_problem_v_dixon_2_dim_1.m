function H = hess_func_for_problem_v_dixon_2_dim_1(x)
	H = [ 2+12*x(1)^2-4*x(2)  -4*x(1);
               -4*x(1)           4   ];
end
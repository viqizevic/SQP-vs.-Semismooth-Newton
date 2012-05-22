function H = hess_func_for_problem_v_himmelblau(x)
	H = [ 12*x(1)^2+4*x(2)-42    4*(x(1)+x(2));
             4*(x(1)+x(2))    4*x(1)+12*x(2)^2-26  ];
end
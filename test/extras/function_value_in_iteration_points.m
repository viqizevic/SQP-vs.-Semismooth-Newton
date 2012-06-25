function function_value_in_iteration_points()
	%testproblem = 'test_problem_v_beale_1';
	%func = 'func_for_problem_v_beale_1';
	testproblem = 'test_problem_v_dixon';
	func = 'func_for_problem_v_dixon';
	[x_ssn,it_ssn,t_ssn,x_sqp,it_sqp,t_sqp,X_ssn,X_sqp] = feval(testproblem,2);
	V = X_ssn;
	W = X_sqp;
	[m,n] = size(V);
	[p,n] = size(W);
	r = max(m,p);
	s = min(m,p);
	for i=1:r
		if i <= s
			U(i,1) = feval(func,V(i,:)');
			U(i,2) = feval(func,W(i,:)');
		else
			if m == r
				U(i,1) = feval(func,V(i,:)');
				U(i,2) = 1.23456789;
			else
				U(i,1) = 1.23456789;
				U(i,2) = feval(func,W(i,:)');
			end
		end
	end
	U
end
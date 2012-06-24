function function_value_in_iteration_points(testproblem,func)
	[x_ssn,it_ssn,t_ssn,x_sqp,it_sqp,t_sqp,X_ssn,X_sqp] = feval(testproblem,2);
	V = X_ssn;
	[m,n] = size(V);
	for i=1:m
		x = V(i,:)';
		feval(func,x)
	end
	V = X_sqp;
	[m,n] = size(V);
	for i=1:m
		x = V(i,:)';
		feval(func,x)
	end
end
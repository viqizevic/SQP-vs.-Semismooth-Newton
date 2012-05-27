function A = opt_ctrl_prob_constr_A (D, B, T, N)
	tao = T/N;
	A = eye(2*(N+1));
	E = -(eye(2)+tao*D);
	d = -tao*B;
	for i=1:N
		j = 2*i+1;
		A(j:j+1,j-2:j-1) = E;
		A(j:j+1,(2*(N+1))+i) = d;
	end
end
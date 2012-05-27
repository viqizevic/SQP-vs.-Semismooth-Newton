function r = opt_ctrl_prob_contsr_r (u_min, u_max, N)
	r = zeros(2*N,1);
	for i=1:N
		j = 2*i-1;
		r(j:j+1,1) = [-u_min; u_max];
	end
end
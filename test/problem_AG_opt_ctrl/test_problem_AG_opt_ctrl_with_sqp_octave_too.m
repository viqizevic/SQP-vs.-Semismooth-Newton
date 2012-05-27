function test_problem_AG_opt_ctrl_with_sqp_octave_too(show)
    u = [];
    v = [];
    x0 = [0.087; 0.000; 0.087; -0.009; 0.086; -0.016; 0.085; -0.023; 0.082; -0.030; 0.079; -0.036; 0.076; -0.042; 0.072; -0.048; 0.067; -0.054; 0.061; -0.059; 0.055; -0.065; 0.002; 0.009; 0.015; 0.018; 0.020; 0.020; 0.019; 0.016; 0.012; 0.006];
    tol = 0.00001;
    itmax = 100;
    A = opt_ctrl_prob_constr_A([0 1; -1 0],[0; 1],1,10);
    b = opt_ctrl_prob_constr_b(pi/36,0,10);
    G = opt_ctrl_prob_constr_G(10);
    r = opt_ctrl_prob_contsr_r(-1,1,10);
    G = [ G; -eye(length(u)); eye(length(v)) ];
    r = [ r; -u; v ];
    tic;
    [x_ssn,fval_ssn,it_ssn] = semismooth_newton('func_for_problem_AG_opt_ctrl','grad_func_for_problem_AG_opt_ctrl','hess_func_for_problem_AG_opt_ctrl',A,b,G,r,x0,itmax,tol);
    t_ssn = toc;
    x1 = sprintf('%.3f ',x_ssn);
    f1 = sprintf('f(x_ssn) = %.3f',fval_ssn);
    t1 = sprintf('solved in %.2f ms.',t_ssn*1000);
    str1 = ['x_ssn = [ ', x1, '], ', f1, ', it = ', num2str(it_ssn), ', ', t1];
    tic;
    [x_sqp,fval_sqp,it_sqp] = seq_quad_prog('func_for_problem_AG_opt_ctrl','grad_func_for_problem_AG_opt_ctrl','hess_func_for_problem_AG_opt_ctrl',A,b,G,r,x0,itmax,tol);
    t_sqp = toc;
    x2 = sprintf('%.3f ',x_sqp);
    f2 = sprintf('f(x_sqp) = %.3f',fval_sqp);
    t2 = sprintf('solved in %.2f ms.',t_sqp*1000);
    str2 = ['x_sqp = [ ', x2, '], ', f2, ', it = ', num2str(it_sqp), ', ', t2];
    tic;
    if (length(b) == 0)
        [x_oct,fval_oct,info_oct,it_oct] = sqp(x0,@phi,[],@g);
    end
    if (length(r) == 0)
        [x_oct,fval_oct,info_oct,it_oct] = sqp(x0,@phi,@h,[]);
    end
    if (length(b) ~= 0 && length(r) ~= 0)
        [x_oct,fval_oct,info_oct,it_oct] = sqp(x0,@phi,@h,@g);
    end
    t_oct = toc;
    x3 = sprintf('%.3f ',x_oct);
    f3 = sprintf('f(x_oct) = %.3f',fval_oct);
    t3 = sprintf('solved in %.2f ms.',t_oct*1000);
    str3 = ['x_oct = [ ', x3, '], ', f3, ', it = ', num2str(it_oct), ', ', t3];
    if ( nargin == 0 )
        show = 1;
    end
    if ( show == 1 )
        u = sprintf('%.3f ',u);
        v = sprintf('%.3f ',v);
        x0 = sprintf('%.3f ',x0);
        str0 = ['u = [ ', u, '], v = [ ', v, '], x0 = [ ', x0, ']'];
        disp(str0);
        disp(str1);
        disp(str2);
        disp(str3);
    end
end

function obj = phi(x)
    obj = func_for_problem_AG_opt_ctrl(x);
end

function c = h(x)
    A = opt_ctrl_prob_constr_A([0 1; -1 0],[0; 1],1,10);
    b = opt_ctrl_prob_constr_b(pi/36,0,10);
    c = b - A*x;
end

function s = g(x)
    u = [];
    v = [];
    G = opt_ctrl_prob_constr_G(10);
    r = opt_ctrl_prob_contsr_r(-1,1,10);
    G = [ G; -eye(length(u)); eye(length(v)) ];
    r = [ r; -u; v ];
    s = r - G*x;
end

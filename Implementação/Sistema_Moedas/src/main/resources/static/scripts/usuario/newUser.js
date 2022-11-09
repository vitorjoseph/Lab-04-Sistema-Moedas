 $(document).ready(() => {
            // altera de estudante para empresa
            $('#dadosAluno').hide()
            $('#dadosEmpresa').hide()
            $('[name=tipo]').each(function () {
                $(this).change(function () {
                    if ($(this).val() == 'tipoEmpresa') {
                        $('#dadosEmpresa').show()
                        $('#dadosAluno').hide()
                        $('#form').prop('action', '/empresa/new')
                        $('.dados-aluno').each(function () {
                            $(this).prop('required', false)
                        })
                        $('.dados-empresa').each(function () {
                            $(this).prop('required', true)
                        })
                    } else if ($(this).val() == 'tipoAluno') {
                        $('#dadosAluno').show()
                        $('#dadosEmpresa').hide()
                        $('#form').prop('action', '/aluno/new')
                        $('.dados-aluno').each(function () {
                            $(this).prop('required', true)
                        })
                        $('.dados-empresa').each(function () {
                            $(this).prop('required', false)
                        })
                    }
                })
            })

            // busca e preenche endereço
            function limpa_formulário_cep() {
                $("#rua").val("");
                $("#bairro").val("");
                $("#cidade").val("");
                $("#estado").val("");
                $("#ibge").val("");
            }

            $("#cep").blur(function () {

                //Nova variável "cep" somente com dígitos.
                var cep = $(this).val().replace(/\D/g, '');

                //Verifica se campo cep possui valor informado.
                if (cep != "") {

                    //Expressão regular para validar o CEP.
                    var validacep = /^[0-9]{8}$/;

                    //Valida o formato do CEP.
                    if (validacep.test(cep)) {

                        //Preenche os campos com "..." enquanto consulta webservice.
                        $("#rua").val("...");
                        $("#bairro").val("...");
                        $("#cidade").val("...");
                        $("#estado").val("...");
                        $("#ibge").val("...");

                        //Consulta o webservice viacep.com.br/
                        $.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?", function (dados) {

                            if (!("erro" in dados)) {
                                //Atualiza os campos com os valores da consulta.
                                $("#rua").val(dados.logradouro);
                                $("#bairro").val(dados.bairro);
                                $("#cidade").val(dados.localidade);
                                $("#estado").val(dados.uf);
                                $("#ibge").val(dados.ibge);
                            } //end if.
                            else {
                                //CEP pesquisado não foi encontrado.
                                limpa_formulário_cep();
                                alert("CEP não encontrado.");
                            }
                        });
                    } //end if.
                    else {
                        //cep é inválido.
                        limpa_formulário_cep();
                        alert("Formato de CEP inválido.");
                    }
                } //end if.
                else {
                    //cep sem valor, limpa formulário.
                    limpa_formulário_cep();
                }
            });

            $('input[readonly]').each(function () {
                $(this).focus(function () {
                    $('#cep').focus()
                })
            })

            // mascara para os campos
            $('#cpf').mask('000.000.000-00')
            $('#cnpj').mask('00.000.000/0000-00')
            $('#cep').mask('00.000-000')

            //valida o formulario
            $('input').each(function () {
                $(this).blur(function () {
                    if ($(this).val() == "") {
                        $(this).toggleClass('form-invalid', true)
                        $(this).nextAll('.error').toggleClass('d-none', false)
                    }
                    else {
                        $(this).toggleClass('form-invalid', false)
                        $(this).nextAll('.error').toggleClass('d-none', true)
                    }
                })
            })

            function validaCpf(cpf) {
                var ver1 = 10
                var ver2 = 11
                cpf = cpf.replace(/[.-]/g, '')

                if (cpf.length === 11)
                    if (!cpf.split('').every((num, i, arr) => num == arr[0])) {
                        let aux = 0
                        for (i = 0; i < 9; i++) {
                            aux += cpf[i] * ver1--
                        }
                        let dv1 = (aux * 10) % 11
                        dv1 = dv1 == 10 ? 0 : dv1

                        let aux2 = 0
                        for (i = 0; i < 10; i++) {
                            aux2 += cpf[i] * ver2--
                        }

                        let dv2 = (aux2 * 10) % 11
                        dv2 = dv2 == 10 ? 0 : dv2

                        if (cpf[9] == dv1 && cpf[10] == dv2)
                            return true
                    }
                return false
            }

            function validaCnpj(cnpj) {
                cnpj = cnpj.replace(/[./-]/g, '');
                if (cnpj.length != 14)
                    return false;

                if (cnpj.split('').every((num, i, arr) => num == arr[0]))
                    return false;

                tamanho = cnpj.length - 2
                numeros = cnpj.substring(0, tamanho);
                digitos = cnpj.substring(tamanho);
                soma = 0;
                pos = tamanho - 7;
                for (i = tamanho; i >= 1; i--) {
                    soma += numeros.charAt(tamanho - i) * pos--;
                    if (pos < 2)
                        pos = 9;
                }
                resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
                if (resultado != digitos.charAt(0))
                    return false;

                tamanho = tamanho + 1;
                numeros = cnpj.substring(0, tamanho);
                soma = 0;
                pos = tamanho - 7;
                for (i = tamanho; i >= 1; i--) {
                    soma += numeros.charAt(tamanho - i) * pos--;
                    if (pos < 2)
                        pos = 9;
                }
                resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
                if (resultado != digitos.charAt(1))
                    return false;

                return true;

            }

            $('#cpf').blur(function () {
                if (!validaCpf($(this).val())) {
                    $(this).toggleClass('form-invalid', true)
                    $(this).nextAll('.error').text("CPF invalido")
                    $(this).nextAll('.error').toggleClass('d-none', false)
                }
            })

            $('#cnpj').blur(function () {
                if (!validaCnpj($(this).val())) {
                    $(this).toggleClass('form-invalid', true)
                    $(this).nextAll('.error').text("CNPJ invalido")
                    $(this).nextAll('.error').toggleClass('d-none', false)
                }
            })

            function tamSenha() {
                if ($('#senha').val().length < 8) {
                    $('#senha').toggleClass('form-invalid', true)
                    $('#senha').nextAll('.error').text("Sua senha precisa ter ao menos 8 caracteres")
                    $('#senha').nextAll('.error').toggleClass('d-none', false)
                } else {
                    $('#senha').toggleClass('form-invalid', false)
                    $('#senha').nextAll('.error').toggleClass('d-none', true)
                }
            }

            function senhasIguais() {
                if ($('#confirmaSenha').val() !== $('#senha').val()) {
                    $('#confirmaSenha').toggleClass('form-invalid', true)
                    $('#confirmaSenha').nextAll('.error').text("As senhas não coincidem")
                    $('#confirmaSenha').nextAll('.error').toggleClass('d-none', false)
                } else {
                    $('#confirmaSenha').toggleClass('form-invalid', false)
                    $('#confirmaSenha').nextAll('.error').toggleClass('d-none', true)
                }
            }

            $('#senha').on('input', tamSenha).blur(tamSenha)

            $('#confirmaSenha').on('input', senhasIguais).blur(senhasIguais)
        })


    